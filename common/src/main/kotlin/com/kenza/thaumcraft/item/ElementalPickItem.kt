package com.kenza.thaumcraft.item

import com.kenza.thaumcraft.MixinFields
import com.kenza.thaumcraft.reg.SoundFX
import com.kenza.thaumcraft.screen.interfaces.RadialPanelHandleable
import io.kenza.support.annotations.NbtKey
import io.kenza.support.utils.*
import io.kenza.support.utils.kotlin.collectAsSet
import net.minecraft.block.BlockState
import net.minecraft.block.OreBlock
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.*
import net.minecraft.nbt.StringNbtReader
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import kotlin.math.absoluteValue


class ElementalPickItem(material: ToolMaterial?, attackDamage: Int, attackSpeed: Float, settsings: Settings?) :
    PickaxeItem(
        material, attackDamage, attackSpeed, settsings
    ), RadialPanelHandleable {

    override fun postMine(
        stack: ItemStack?,
        world: World?,
        state: BlockState?,
        pos: BlockPos?,
        miner: LivingEntity?,
    ): Boolean {
        if (world?.isClient == true)  super.postMine(stack, world, state, pos, miner)

        (miner as? PlayerEntity)?.let {
            val itemContext = getItemContext(world, it, it.activeHand, pos)
            scanOreByPick(itemContext)
        }
        return super.postMine(stack, world, state, pos, miner)
    }


    override fun useOnBlock(context: ItemUsageContext?): ActionResult {
        if (context?.world?.isClient == true) return ActionResult.SUCCESS

        with(context) {
            val itemStack = context?.player?.getStackInHand(context.hand) ?: return@with
            val player = context.player ?: return@with

            if (!player.isInSneakingPose) {
                scanOreByPick(context)
                itemStack.incremenDamage()
                return@with
            }

            val blockState = context.blockPos?.run {
                context.world?.getBlockState(this)
            }

            val selectedOreRawId = if (blockState?.block is OreBlock) {
                SoundFX.cameraticks.playSound(context.world, context.blockPos, player)
                blockState.block.id().toString()
            } else {
                SoundFX.playCameraClack(context.world, context.blockPos, player)
                null
            }


            itemStack.changeData<ElementalPickItemData> {
                it.copy(
                    selectedOreRawId = selectedOreRawId
                )
            }



            context.player?.setStackInHand(context.hand, itemStack)
        }


        return ActionResult.SUCCESS// super.useOnBlock(context)
    }

    override fun onRadialPanelItemSelected(selectedItemId: Identifier, player: PlayerEntity) {
        player.mainHandStack.changeData<ElementalPickItemData> {
            it.copy(
                selectedOreRawId = if(selectedItemId.isEmpty()){
                    null
                }else{
                    selectedItemId.toString()
                }
            )
        }
        SoundFX.cameraticks.playSound(player.world, player.blockPos, player)
    }

    fun getSelectedOreStack(itemStack: ItemStack): ItemStack? {
        return itemStack.readData<ElementalPickItemData>().selectedOreId?.run {
            parseOre(this)
        }
    }

    private fun parseOre(selectedOreId: Identifier): ItemStack {
        val json =
            "{Count:1b,id:\"${selectedOreId.toString()}\"}"
        return ItemStack.fromNbt(StringNbtReader.parse(json))
    }

    private fun getItemContext(world: World?, user: PlayerEntity, hand: Hand?, pos: BlockPos?): ItemUsageContext {
        return ItemUsageContext(user, hand, MixinFields.tryBreakBlock_ServerPlayerInteractionManager_raycasted_block)
    }

    private fun scanOreByPick(
        context: ItemUsageContext?,
    ) {

        val itemStack = context?.player?.getStackInHand(context.hand) ?: return
        val player = context.player ?: return
        val world = context.world ?: return
        val selectedOreId = itemStack.readData<ElementalPickItemData>().selectedOreId ?: return
//        val side = context.playerFacing?.opposite ?: return@with
        val side = context.side ?: return

        val blockPos = kotlin.runCatching { context.blockPos?.toVec3d() }.getOrNull() ?: return

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
                    .add(offsetX, offsetY, offsetZ),
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
                } else if (blocksScore < MIN_SCORE_ACTIVATION) {
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
                        return
                    }

//                    context.player?.setStackInHand(context.hand, itemStack)

                    val soundVolume = blocksScore.cutByRange(0.3, 1.0).toFloat() - KMath.getBetween(0.0f, 0.05f)
                    if (isEmitAction) {

                        SoundFX.neutralization.playSound(
                            world, player.blockPos, player, soundVolume,
                            1f - KMath.getBetween(0f, 0.05f)
                        )
//                        world.playSound(
//                            player,
//                            player.blockPos,
//                            SoundFX.neutralization.soundEvent,
//                            SoundCategory.AMBIENT,
//
//                        )
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

