package com.kenza.thaumcraft.item

import io.kenza.support.utils.kotlin.isNull
import io.kenza.support.utils.kotlin.safeCast
import net.minecraft.entity.Entity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ArmorItem
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class BootsTravellerItem(
    material: ArmorMaterial?,
    slot: EquipmentSlot?,
    settings: Settings?,
) : ArmorItem(material, slot, settings) {

    override fun inventoryTick(stack: ItemStack?, world: World, entity: Entity?, slot: Int, selected: Boolean) =
        with(entity.safeCast<PlayerEntity>()) {
            this ?: return@with

            super.inventoryTick(stack, world, entity, slot, selected)
//            if (world.isClient()) {
//                return
//            }

            val boots = inventory.getArmorStack(0)
            if (boots.item.safeCast<BootsTravellerItem>().isNull()) {
                return@with
            }


            if (!isFallFlying) {
                movementSpeed = 2f
            }

        }


}

