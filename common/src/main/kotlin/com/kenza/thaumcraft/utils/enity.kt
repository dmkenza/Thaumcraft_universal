package com.kenza.thaumcraft.utils

import com.kenza.thaumcraft.item.BootsTravellerItem
import io.kenza.support.utils.kotlin.isNull
import io.kenza.support.utils.kotlin.safeCast
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity

object EntityUtil {

    @JvmField
    val TRAVEL_BOOTS_SPEED_FACTOR : Double = 1.3

    @JvmField
    val TRAVEL_BOOTS_JUMP_FACTOR : Float = 1.3f

    @JvmStatic
    fun shouldIncreaseSpeedFactor(entity: Any): Boolean {
        return with(entity.safeCast<PlayerEntity>()) {
            this ?: return@with false
            val boots = inventory.getArmorStack(0)
            if (boots.item.safeCast<BootsTravellerItem>().isNull()) {
                return@with false
            }
            return@with true
        }
    }

    @JvmStatic
    fun shouldIncreaseJumpFactor(entity: Any): Boolean {
        return with(entity.safeCast<PlayerEntity>()) {
            this ?: return@with false
            val boots = inventory.getArmorStack(0)
            if (boots.item.safeCast<BootsTravellerItem>().isNull()) {
                return@with false
            }
            return@with true
        }
    }

}

