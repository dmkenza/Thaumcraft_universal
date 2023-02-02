package com.kenza.thaumcraft.screen

import com.kenza.thaumcraft.RadialMenuScreen1
import io.github.cottonmc.cotton.gui.GuiDescription
import io.github.cottonmc.cotton.gui.client.CottonClientScreen

class ClientScreen(val gui: GuiDescription) : CottonClientScreen(gui) {


    override fun keyPressed(ch: Int, keyCode: Int, modifiers: Int): Boolean {
        (gui as? RadialMenuScreen1)?.keyBinding?.let {
            if (it.matchesKey(ch ,keyCode)) {
                gui.onKeyPressed()
            }
        }

        return super.keyPressed(ch, keyCode, modifiers)
    }


    override fun shouldPause(): Boolean {
        return false
    }
}