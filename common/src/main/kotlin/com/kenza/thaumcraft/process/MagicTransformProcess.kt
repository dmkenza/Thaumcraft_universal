package com.kenza.thaumcraft.process

import com.kenza.thaumcraft.Debug
import com.kenza.thaumcraft.block.ArcanePedestalBlockEntity
import com.kenza.thaumcraft.reg.ELEMENTAL_PICK
import com.kenza.thaumcraft.reg.SALIS_MUNDUS_ITEM
import io.kenza.support.utils.chatMsg
import io.kenza.support.utils.identifier
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.item.PickaxeItem
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object MagicTransformProcess {

    val BYG_PENDORITE_PICKAXE_ID = identifier("byg:pendorite_pickaxe")

    val transormerItems = listOf(
        SALIS_MUNDUS_ITEM
    )


    fun isTransformer(itemOnHand: ItemStack?): Boolean {
        return itemOnHand?.item?.asItem() == SALIS_MUNDUS_ITEM.get()
    }

    fun arcaneItemTransform(
        player: ServerPlayerEntity,
        entity: ArcanePedestalBlockEntity,
        state: BlockState,
        world: World,
        pos: BlockPos,
    ) {

        chatMsg("test")

        world
            .playSound(
                null, pos,
                SoundEvents.UI_BUTTON_CLICK, SoundCategory.AMBIENT, 1f, 1f
            )


        val itemInPedestal = entity.items.firstOrNull() ?: return

        if (itemInPedestal.item.`arch$registryName`() == BYG_PENDORITE_PICKAXE_ID){
            val nbt = itemInPedestal.nbt
            val newItem = ELEMENTAL_PICK.get().defaultStack.apply {
                setNbt(nbt)
            }
            entity.setStack(0,newItem)
        }else{
            return
        }

        if (!player.isCreative) {
            player.mainHandStack.decrement(1)
        }

        entity.markDirty()


//        ItemScatterer.spawn(world, pos, entity.items)
//        entity.items
    }

}