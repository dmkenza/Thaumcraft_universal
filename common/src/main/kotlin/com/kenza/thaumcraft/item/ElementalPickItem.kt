package com.kenza.thaumcraft.item

import com.kenza.thaumcraft.MixinFields
import com.kenza.thaumcraft.base.gson
import io.kenza.support.utils.*
import net.minecraft.block.BlockState
import net.minecraft.block.OreBlock
import net.minecraft.client.gui.screen.Screen
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.PickaxeItem
import net.minecraft.item.ToolMaterial
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.RaycastContext
import net.minecraft.world.World
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.math.absoluteValue
import kotlin.reflect.full.findAnnotation


class ElementalPickItem(material: ToolMaterial?, attackDamage: Int, attackSpeed: Float, settsings: Settings?) :
    PickaxeItem(
        material, attackDamage, attackSpeed, settsings
    ) {

//    val testId = identifier("minecraft:iron_ore")


    override fun postMine(
        stack: ItemStack?,
        world: World?,
        state: BlockState?,
        pos: BlockPos?,
        miner: LivingEntity?,
    ): Boolean {
        (miner as? PlayerEntity)?.let {
            val itemContext = getItemContext(world, it, it.activeHand, pos)
            scanOreByPick(itemContext)
        }
        return super.postMine(stack, world, state, pos, miner)
    }



    override fun useOnBlock(context: ItemUsageContext?): ActionResult {

        with(context) {

            if(!Screen.hasShiftDown()) {
                scanOreByPick(context)
                return@with
            }

            val itemStack = context?.player?.getStackInHand(context.hand) ?: return@with
            val blockState = context.blockPos?.run {
                context.world?.getBlockState(this)
            }

            if(blockState?.block is OreBlock){
                itemStack.changeData<ElementalPickItemData> {
                    it.copy(
                        selectedOreRawId = blockState.block.id().toString()
                    ).also {
                        chatMsg(it.selectedOreRawId!!)
                    }
                }
            }
            context.player?.setStackInHand(context.hand, itemStack.copy())
        }


        return super.useOnBlock(context)
    }


    private fun getItemContext(world: World?, user: PlayerEntity, hand: Hand?, pos: BlockPos?): ItemUsageContext {
        return ItemUsageContext(user, hand, MixinFields.tryBreakBlock_ServerPlayerInteractionManager_raycasted_block)
    }

    private fun scanOreByPick(
        context: ItemUsageContext?,
    ) = with(context) {

        val itemStack = context?.player?.getStackInHand(context.hand) ?: return@with
        val world =  this@with?.world ?: return@with
        val selectedOreId = itemStack.readData<ElementalPickItemData>().selectedOreId ?: return@with

        val side = context.playerFacing?.opposite ?: return@with
//        val side = this@with?.side ?: return@with

        val blockPos = kotlin.runCatching { context.blockPos?.toVec3d() }.getOrNull() ?: return@with

        val blocks = (1..5).map { t ->
            val offsetX = side.offsetX * -1.0 * t + side.offsetX
            val offsetY = side.offsetY * -1.0 * t
            val offsetZ = side.offsetZ * -1.0 * t + side.offsetY
            val offsetX2 = (1 - side.offsetX.absoluteValue) * -1.0
            val offsetY2 = (1 - side.offsetY.absoluteValue) * -1.0
            val offsetZ2 = (1 - side.offsetZ.absoluteValue) * -1.0
            net.minecraft.util.math.Box.of(
                blockPos
                    .center()
                    .add(offsetX, offsetY , offsetZ),
                offsetX2 * 2 * t, (offsetY2 + t), offsetZ2 * 2 * t
            )
        }.map {
            net.minecraft.util.math.BlockPos.stream(it).map {
                it.mutableCopy()
            }.collectAsSet()
        }.reduce { acc, blockPos ->
            acc.addAll(blockPos)
            acc
        }

//            if (isRenderThread()) {
        blocks.apply {
            map { pos ->
                val pos = pos.toVec3d().center()
                context.world?.addParticle(
                    net.minecraft.particle.ParticleTypes.FLAME, pos.getX().toDouble(),
                    pos.getY().toDouble(), pos.getZ().toDouble(),
                    0.0, 0.0005, 0.0
                )
            }
            val score = map { pos ->
                val bs = world.getBlockState(pos)
                if (bs.block.id() == selectedOreId) {
                    1.0
                } else {
                    0.0
                }
            }.sum()

            if (score >= 1) {

                chatMsg("test")
                world.playSound(
                    null, player?.blockPos,
                    net.minecraft.sound.SoundEvents.UI_BUTTON_CLICK, net.minecraft.sound.SoundCategory.AMBIENT, 1f, 1f
                )

            }
        }
//            }

    }
}

@NbtKey("elemental_pick_data")
data class ElementalPickItemData(
    val selectedOreRawId: String? = null,
) {
    val selectedOreId: Identifier?
        get() = selectedOreRawId?.run(::identifier)
}


@Retention(RetentionPolicy.RUNTIME)
annotation class NbtKey(val value: String)


private inline fun <reified T> ItemStack?.readData(): T {
    val key = T::class.findAnnotation<NbtKey>()?.value ?: throw Exception("Data Class should have NbtKey")
    val data = readJsonNbt(key).run {
        gson.fromJson(this, T::class.java) ?: T::class.createDefaultObject() as T
    }
    return data
}


private inline fun <reified T> ItemStack?.changeData(changeFun: (old: T) -> T): T {

    val key = T::class.findAnnotation<NbtKey>()?.value ?: throw Exception("Data Class should have NbtKey")
    val old = readJsonNbt(key).run {
        gson.fromJson(this, T::class.java) ?: T::class.createDefaultObject() as T
    }
    val new = changeFun.invoke(old)

    writeJsonNbt(key) {
        gson.toJson(new)
    }
    return new
}
