package com.kenza.thaumcraft.screen.interfaces

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Identifier

interface RadialPanelHandleable {
    fun onRadialPanelItemSelected(selectedItemId: Identifier, player: PlayerEntity)
}