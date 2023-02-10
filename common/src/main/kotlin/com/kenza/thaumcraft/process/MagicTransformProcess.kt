package com.kenza.thaumcraft.process

import com.kenza.thaumcraft.block.ArcanePedestalBlockEntity
import com.kenza.thaumcraft.recipe.InfusionRecipe
import com.kenza.thaumcraft.reg.ELEMENTAL_PICK
import com.kenza.thaumcraft.reg.SALIS_MUNDUS_ITEM
import com.kenza.thaumcraft.reg.SoundFX
import io.kenza.support.utils.chatMsg
import io.kenza.support.utils.identifier
import net.minecraft.block.BlockState
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

object MagicTransformProcess {

    val BYG_PENDORITE_PICKAXE_ID = identifier("byg:pendorite_pickaxe")

    val transormerItems = listOf(
        SALIS_MUNDUS_ITEM
    )


    fun isTransformer(itemOnHand: ItemStack?): Boolean {
        return itemOnHand?.item?.asItem() == SALIS_MUNDUS_ITEM.get()
    }

    fun tryMakeInfusion(
        player: ServerPlayerEntity,
        entity: ArcanePedestalBlockEntity,
        state: BlockState,
        world: World,
        pos: BlockPos,
    ) {


        val world: World = entity.world ?: return
        val inventory1 = SimpleInventory(entity.items.size)

        entity.items.forEachIndexed { i, itemStack ->
            inventory1.setStack(i, entity.items.get(i))
        }

        val itemInPedestal = entity.items.firstOrNull() ?: return

        val match: Optional<InfusionRecipe> = world.recipeManager
            .getFirstMatch<SimpleInventory, InfusionRecipe>(InfusionRecipe.Type.INSTANCE, inventory1, world)

        if (match.isPresent()) {

            val nbt = itemInPedestal.nbt
            val newItem =  match.get().output.item.defaultStack.apply {
                setNbt(nbt)
            }

            entity.removeStack(0, 1)
            entity.setStack(0, newItem)

            if (!player.isCreative) {
                player.mainHandStack.decrement(1)
            }


            SoundFX.playWandSound(world, pos)


            entity.markDirty()
        }
    }

}