package com.kenza.thaumcraft

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.*
import io.github.cottonmc.cotton.gui.widget.data.Axis
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon
import net.minecraft.item.Items
import net.minecraft.text.Text

class ScrollingTestGui : LightweightGuiDescription() {
    init {
        val root = rootPanel as WGridPanel
        val box = WBox(Axis.VERTICAL)
        for (i in 0..19) {
            box.add(WLabeledSlider(0, 10, Text.literal("Slider #$i")))
        }
        box.add(WButton(ItemIcon(Items.APPLE)))
        root.add(WLabel(Text.literal("Scrolling test")).setVerticalAlignment(VerticalAlignment.CENTER), 0, 0, 5, 2)
        root.add(WScrollPanel(box), 0, 2, 5, 3)
        root.validate(this)
    }
}
