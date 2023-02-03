package com.kenza.thaumcraft.screen

import io.github.cottonmc.cotton.gui.GuiDescription
import io.github.cottonmc.cotton.gui.client.CottonClientScreen
import net.minecraft.client.util.math.MatrixStack

class ClientScreen(val gui: GuiDescription) : CottonClientScreen(gui) {

    override fun keyPressed(ch: Int, keyCode: Int, modifiers: Int): Boolean {
        (gui as? RadialMenuGui)?.keyBinding?.let {
            if (it.matchesKey(ch ,keyCode)) {
                gui.onKeyPressed()
                return false
            }
        }
        return  false
    }

    init {
        passEvents = true
    }

    override fun keyReleased(ch: Int, keyCode: Int, modifiers: Int): Boolean {
        (gui as? RadialMenuGui)?.keyBinding?.let {
            if (it.matchesKey(ch ,keyCode)) {
                gui.onKeyReleased()
                return false
            }
        }
        return super.keyReleased(ch, keyCode, modifiers)
    }

    override fun renderBackground(matrices: MatrixStack?) {
        //super.renderBackground(matrices)
    }

    override fun renderBackgroundTexture(vOffset: Int) {
        //super.renderBackgroundTexture(vOffset)
    }

    override fun shouldPause(): Boolean {
        return false
    }
}