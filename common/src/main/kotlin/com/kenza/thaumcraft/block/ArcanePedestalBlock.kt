package com.kenza.thaumcraft.block

import com.google.common.collect.ImmutableMap
import io.kenza.support.utils.getRegBlockEntityType
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.function.Function

class ArcanePedestalBlock(
    val id: Identifier,
    settings: AbstractBlock.Settings,
) : BlockWithEntity(settings) {

    val SHAPE: VoxelShape = VoxelShapes.union(
        createCuboidShape(0.01, 0.01, .01, 16.0, 4.0, 16.0),
        createCuboidShape(2.0, 12.0, 2.0, 14.0, 16.0, 14.0),
        createCuboidShape(4.0, 4.0, 4.0, 12.0, 12.0, 12.0)
    ).simplify()

    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?,
    ): VoxelShape {
        return SHAPE
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun getShapesForStates(stateToShape: Function<BlockState, VoxelShape>?): ImmutableMap<BlockState, VoxelShape> {
        return super.getShapesForStates(stateToShape)
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        val type = id.getRegBlockEntityType<ArcanePedestalBlockEntity>()
        return ArcanePedestalBlockEntity(type, pos, state)
    }

    override fun <T : BlockEntity?> getTicker(
        world: World,
        state: BlockState?,
        type: BlockEntityType<T>?,
    ): BlockEntityTicker<T>? {
        return BlockEntityTicker { _, _, _, blockEntity -> (blockEntity as? ArcanePedestalBlockEntity)?.tick() }
    }


    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hitResult: BlockHitResult,
    ): ActionResult {
        if (world.isClient) return ActionResult.SUCCESS

        val slot = 0
        val blockEntity: ArcanePedestalBlockEntity? = world.getBlockEntity(pos) as? ArcanePedestalBlockEntity

        val itemStackInSlot = blockEntity?.items?.getOrNull(slot)

        if (itemStackInSlot?.isEmpty == true) {
            blockEntity.items.set(slot, player.mainHandStack.copy())
            player.mainHandStack.decrement(1)
            blockEntity.markDirty()
        } else {
            player.inventory.offerOrDrop(itemStackInSlot);
            itemStackInSlot?.decrement(1)
        }

        return ActionResult.SUCCESS
    }


    @Suppress("DEPRECATION")
    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        val oldBlockEntity = world.getBlockEntity(pos) as? ArcanePedestalBlockEntity
        super.onStateReplaced(state, world, pos, newState, moved)

        if (oldBlockEntity?.items?.isNotEmpty() == true) {
            ItemScatterer.spawn(world, pos, oldBlockEntity.items)
            world.updateComparators(pos, this)
        }
    }
}