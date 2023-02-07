package com.kenza.thaumcraft.item

import com.kenza.thaumcraft.MixinFields
import com.kenza.thaumcraft.reg.SoundFX
import io.kenza.support.annotations.NbtKey
import io.kenza.support.utils.*
import net.minecraft.block.BlockState
import net.minecraft.block.OreBlock
import net.minecraft.client.gui.screen.Screen
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.*
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.math.absoluteValue


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
            val itemStack = context?.player?.getStackInHand(context.hand) ?: return@with

            if (!Screen.hasShiftDown()) {
                scanOreByPick(context)
                if(context.player?.isCreative != true){
                    itemStack.incremenDamage()
                }
                return@with
            }

            val blockState = context.blockPos?.run {
                context.world?.getBlockState(this)
            }

            val selectedOreRawId = if (blockState?.block is OreBlock) {
                blockState.block.id().toString()
            }else{
                null
            }

            itemStack.changeData<ElementalPickItemData> {
                it.copy(
                    selectedOreRawId = selectedOreRawId
                )
            }
            context.player?.setStackInHand(context.hand, itemStack.copy())
        }


        return ActionResult.CONSUME// super.useOnBlock(context)
    }


    private fun getItemContext(world: World?, user: PlayerEntity, hand: Hand?, pos: BlockPos?): ItemUsageContext {
        return ItemUsageContext(user, hand, MixinFields.tryBreakBlock_ServerPlayerInteractionManager_raycasted_block)
    }

    private fun scanOreByPick(
        context: ItemUsageContext?,
    ) = with(context) {

        val itemStack = context?.player?.getStackInHand(context.hand) ?: return@with
        val player = context.player ?: return@with
        val world = this@with?.world ?: return@with
        val selectedOreId = itemStack.readData<ElementalPickItemData>().selectedOreId ?: return@with

//        val side = context.playerFacing?.opposite ?: return@with
        val side = this@with.side ?: return@with

        val blockPos = kotlin.runCatching { context.blockPos?.toVec3d() }.getOrNull() ?: return@with

//        val oreRange = 5 + KMath.getBetween(0, 4)
        val oreRange = 9 + KMath.getBetween(0, 4)
        val blocks = (1..oreRange).map { t ->
            val offsetX = side.offsetX * -1.0 * t + side.offsetX
            val offsetY = side.offsetY * -1.0 * t
            val offsetZ = side.offsetZ * -1.0 * t + side.offsetY
            val offsetX2 = (1 - side.offsetX.absoluteValue) * -1.0
            var offsetY2 = (1 - side.offsetY.absoluteValue) * -1.0
            val offsetZ2 = (1 - side.offsetZ.absoluteValue) * -1.0


            var offsetY3 = offsetY2.absoluteValue * 2 * t
            if (offsetY3 > 2) {
                offsetY3 = 2.0
            }

            net.minecraft.util.math.Box.of(
                blockPos
                    .add(offsetX, offsetY , offsetZ),
                offsetX2 * 2 * t, (offsetY3), offsetZ2 * 2 * t
            )
        }.map {
            BlockPos.stream(it).map {
                it.mutableCopy()
            }.collectAsSet()
        }.reduce { acc, blockPos ->
            acc.addAll(blockPos)
            acc
        }

        val MIN_SCORE_ACTIVATION = 0.1

//            if (isRenderThread()) {
        blocks.apply {
//            map { pos ->
//                val pos = pos.toVec3d().center()
//
//                context.world?.addParticle(
//                    net.minecraft.particle.ParticleTypes.FLAME, pos.getX().toDouble(),
//                    pos.getY().toDouble(), pos.getZ().toDouble(),
//                    0.0, 0.0005, 0.0
//                )
//            }

            val blocksScore = mapNotNull { pos ->
                val bs = world.getBlockState(pos)

                if (bs.block.id() == selectedOreId) {
                    val distance = pos.toVec3d().distanceTo(blockPos) - 1
                    var incrementalValue = ((oreRange - distance) / oreRange)

//                    if (incrementalValue <= 0.1) {
//                        incrementalValue = 0.1
//                    }
                    incrementalValue
                } else {
                    null
                }
            }.sum()

//            if (blocksScore <= 0.5) {
//                return@with
//            }


            var isEmitAction = false
            var newScore: Float

            itemStack.readData<ElementalPickItemData>().let {
                newScore = if (it.actionScore >= 1) {
                    isEmitAction = true
                    0f
                } else if (blocksScore <MIN_SCORE_ACTIVATION) {
                    it.actionScore - KMath.getBetween(0.1f, 0.15f)
                } else {
                    it.actionScore + KMath.getBetween(0.34f, 0.5f)
                }

                if (newScore < 0f) {
                    newScore = 0f
                }


                (player as? ServerPlayerEntity)?.let {
                    itemStack.changeData<ElementalPickItemData> {
                        it.copy(
                            actionScore = newScore
                        )
                    }

                    if (blocksScore <= MIN_SCORE_ACTIVATION) {
                        return@with
                    }

//                    context.player?.setStackInHand(context.hand, itemStack)

                    val soundVolume = blocksScore.cutByRange(0.3, 1.0).toFloat() - KMath.getBetween(0.0f, 0.05f)
                    if (isEmitAction) {
                        world.playSound(
                            null,
                            player.blockPos,
                            SoundFX.neutralization.soundEvent,
                            SoundCategory.AMBIENT,
                            soundVolume,
                            1f - KMath.getBetween(0f, 0.05f)
                        )

                    }
                }

            }
        }
    }
}


@NbtKey("elemental_pick_data")
data class ElementalPickItemData(
    val selectedOreRawId: String? = null,
    val actionScore: Float = 0f,
) {
    val selectedOreId: Identifier?
        get() = selectedOreRawId?.run(::identifier)
}

