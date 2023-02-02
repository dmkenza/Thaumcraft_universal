package com.kenza.thaumcraft

import com.kenza.thaumcraft.screen.WHudTest
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.*
import io.github.cottonmc.cotton.gui.widget.data.Insets
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon
import io.kenza.support.utils.chatMsg
import io.kenza.support.utils.mc
import net.minecraft.client.option.KeyBinding
import net.minecraft.item.Items

class RadialMenuScreen1(
    val keyBinding: KeyBinding,
) : LightweightGuiDescription() {

    init {
        onConfig()
    }

    override fun addPainters() {
//        super.addPainters()
    }


    fun onConfig() {

        WHudTest().scale
        val root = (rootPanel as WGridPanel)
//        val box = WBox(Axis.VERTICAL)
////        for (i in 0..19) {
////            box.add(WLabeledSlider(0, 10, Text.literal("Slider #$i")))
//
//        val stack = mc.player?.inventory?.getStack(0)
//
//        var btn: WButton? = null
//
//        btn = WButton(
//            ItemIcon(stack?.item ?: Items.APPLE)
//        ).apply {
//            setOnClick {
//                chatMsg("setOnClick")
//            }
//
//            hoveredProperty().readOnly().addListener { property, from, to ->
////                chatMsg("hoveredProperty1")
////                val hovered = property.get()
////                if (hovered) {
////                    btn?.setSize(10, 10)
////                    btn?.setIconSize( 10)
////                } else {
////                    btn?.setSize(30, 30)
////                    btn?.setIconSize( 50)
////                }
//            }
//        }
//        box.add(btn)
//        root.add(WLabel(Text.literal("Scrolling test")).setVerticalAlignment(VerticalAlignment.CENTER), 0, 0, 5, 2)
//
//        val panel = WScrollPanel(box).apply {
//            val x = TestBackgroundPainter.test()
//            root.setBackgroundPainter(x)
//        }

        chatMsg("14423")
        val stack = mc.player?.inventory?.getStack(0)

        var btn: WButton? = null

        btn = WButton(
            ItemIcon(stack?.item ?: Items.APPLE)
        ).apply {
            setOnClick {
                chatMsg("setOnClick1")
            }

            hoveredProperty().readOnly().addListener { property, from, to ->
                chatMsg("hoveredProperty2")
//                val hovered = property.get()
//                if (hovered) {
//                    btn?.setSize(10, 10)
//                    btn?.setIconSize( 10)
//                } else {
//                    btn?.setSize(30, 30)
//                    btn?.setIconSize( 50)
//                }
            }
        }

        root.add(
            WHudTest().apply {
                setSize(256, 256)
            }, 0, 0, 0, 0
        )

//        root.add(btn, 0, 0, 1, 1)
//
//        root.add( WButton(
//            ItemIcon(stack?.item ?: Items.APPLE)
//        ), 10, 0, 1, 1)

        root.insets = Insets.NONE
//        root.setBackgroundPainter(null)
//        root.validate(this)
    }


    fun onKeyPressed() {
//        chatMsg("onKeyPressed")
    }

}

