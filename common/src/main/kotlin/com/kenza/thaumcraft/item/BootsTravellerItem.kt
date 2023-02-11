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


//                if (this.velocity.lengthSquared() > 1.0E-7) {
//                }

//                this.velocity = this.velocity.multiply(1.55,1.05,1.55)

//                movementMultiplier =  movementMultiplier.add(
//                    this.velocity.add(1.55,1.05,1.55)
//                )

//                val d = this.velocityMultiplier.toDouble()
//                val vec3d = this.velocity.add(Vec3d(0.1,0.0,0.2))
//                this.travel(Vec3d( vec3d.x, vec3d.y, vec3d.z))
//                if (this.isSprinting) {
//                    val f = this.yaw * 0.017453292f
//                    this.velocity = this.velocity.add(
//                        (-MathHelper.sin(f) * 0.2f).toDouble(),
//                        0.0,
//                        (MathHelper.cos(f) * 0.2f).toDouble()
//                    )
//                }

//                this.velocityDirty = true
                movementSpeed = 2f
            }

        }


}

