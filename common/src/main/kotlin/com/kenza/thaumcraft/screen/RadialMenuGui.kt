package com.kenza.thaumcraft.screen

import com.kenza.thaumcraft.MixinFields.setScreen_MinecraftClient_enabled
import com.kenza.thaumcraft.WRadialButton
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.*
import io.github.cottonmc.cotton.gui.widget.data.Insets
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon
import io.kenza.support.utils.chatMsg
import io.kenza.support.utils.extensions.weak
import io.kenza.support.utils.mc
import net.minecraft.client.option.KeyBinding
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import java.lang.ref.WeakReference


class RadialMenuGui(
    val keyBinding: KeyBinding,
) : LightweightGuiDescription(), RadialPanelListener {


    private var radialPlainPanel: WRadialPlainPanel? = null

    init {
        onConfig()
    }

    override fun addPainters() {
//        super.addPainters()
    }

    fun onConfig() {

        val root = (rootPanel as WGridPanel)
        root.setSize(256, 256)
        val stack = mc.player?.inventory?.getStack(0)

        root.add(
            WRadialPlainPanel(256, this).apply {
                radialPlainPanel = this
                setSize(256, 256)
                addRadialBack()


                (0..12).map {
                    createButton(stack)
                }.let {
                    addRadialElement(* it.toTypedArray())
                }


            }, 0, 0, 0, 0
        )

        root.insets = Insets.NONE
    }


    fun createButton(stack: ItemStack?): WRadialButton {
        return WRadialButton(
            ItemIcon(stack?.item?.defaultStack ?: Items.APPLE.defaultStack)
        ).apply {

        }
    }

    override fun onElementSelected(w: WRadialButton) {
        chatMsg(w.toString())
        mc.currentScreen?.close()
    }

    fun onKeyPressed() {
        if (mc.mouse.isCursorLocked) {
            mc.mouse.unlockCursor()
        }
        radialPlainPanel?.radialAnimUp()
    }

    fun onKeyReleased() {
        if (!mc.mouse.isCursorLocked) {
            setScreen_MinecraftClient_enabled = false
            mc.mouse.lockCursor()
            setScreen_MinecraftClient_enabled = true
        }

        radialPlainPanel?.radialAnimDown()
    }

}
