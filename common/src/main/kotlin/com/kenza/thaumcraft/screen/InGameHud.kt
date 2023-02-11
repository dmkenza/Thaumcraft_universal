package com.kenza.thaumcraft.screen

import com.kenza.thaumcraft.WRadialButton
import com.kenza.thaumcraft.item.ElementalPickItem
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WWidget
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon
import io.kenza.support.utils.kotlin.isNull
import io.kenza.support.utils.kotlin.safeCast
import io.kenza.support.utils.mc
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack


class InGameHud : WWidget() {

    init {
        onConfig()
    }

    var showWidget: WRadialButton? = null


    override fun paint(matrices: MatrixStack?, x: Int, y: Int, mouseX: Int, mouseY: Int) {
//        ScreenDrawing.coloredRect(matrices, x, y, width, height, -0xff0100)

        showWidget?.let {
            val pW = (width - it.width) / 2f
            val pH = (height - it.height) / 2f
            showWidget?.paint(matrices, x + pW.toInt(), y + pH.toInt(), mouseX, mouseY)
        }

    }

    override fun tick() {
        var widgetEnable = false

        val itemStack = mc.player?.mainHandStack ?: return

        with(itemStack.item.safeCast<ElementalPickItem>()) {
            this ?: return@with
            val oreItemStack = getSelectedOreStack(itemStack)

            if (showWidget.isNull() || showWidget?.itemStack != oreItemStack ) {
                oreItemStack?.let {
                    showWidget = createWidgetItem(oreItemStack)
                    widgetEnable = true
                }
            }
        }

        if (widgetEnable.not()) {
            showWidget = null
        }
    }


    private fun createWidgetItem(itemStack: ItemStack): WRadialButton {
        return WRadialButton(
            itemStack
        ).apply {
            setSize(32,32)
        }
    }

    fun onConfig() {

    }
}

