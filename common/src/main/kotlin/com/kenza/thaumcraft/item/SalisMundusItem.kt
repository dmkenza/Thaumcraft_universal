package com.kenza.thaumcraft.item

import io.kenza.support.utils.chatMsg
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World

class SalisMundusItem(settings: Settings) : Item(settings) {

    override fun finishUsing(stack: ItemStack?, world: World?, user: LivingEntity?): ItemStack {
        val serverWorld = world as? ServerWorld
//        if (isClientSide()) {
//            return super.finishUsing(stack, world, user)
//        }
//        world?.raycastBlock()

        val pos = user?.blockPos ?: return super.finishUsing(stack, world, user)
//        val newPos = pos.withY(pos.y + 100)
//
//        val list = ArrayList<BlockPos>()

//        val x = BlockView.raycast(pos.toVec3d(), newPos.toVec3d(), list,
//            BiFunction<Any, BlockPos, Any> { t, u ->
//                return@BiFunction u
//            }
//        ) {}

         EntityType.LIGHTNING_BOLT.spawn(
            serverWorld, null, null, null,
            pos, SpawnReason.TRIGGERED, true, true
        )?.apply {
             setCosmetic(true)
         }


        return super.finishUsing(stack, world, user)
    }
}