package com.kenza.thaumcraft.screen

import com.kenza.thaumcraft.MixinFields.setScreen_MinecraftClient_enabled
import com.kenza.thaumcraft.WRadialButton
import com.kenza.thaumcraft.screen.interfaces.RadialPanelHandleable
import com.kenza.thaumcraft.screen.net.ScreenNetworking
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.widget.*
import io.github.cottonmc.cotton.gui.widget.data.Insets
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon
import io.kenza.support.utils.id
import io.kenza.support.utils.isAir
import io.kenza.support.utils.kotlin.safeCast
import io.kenza.support.utils.mc
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.OreBlock
import net.minecraft.client.option.KeyBinding
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry


class RadialMenuGui(
    val keyBinding: KeyBinding,
) : LightweightGuiDescription(), RadialPanelListener {


    private var radialPlainPanel: WRadialPlainPanel? = null

    val singleWords = listOf(
        "diamond", "emerald",
    )

    val regexes = listOf(
        "^raw_", "_cluster$",  "_clusters$","_shards$", "_shards$", "_gem$",  "_gems$", "_scraps$", "_crystal$", "_lazuli$"
    )

    init {
        onConfig()
    }

    override fun onElementSelected(w: WRadialButton) {
        mc.currentScreen?.close()
        ScreenNetworking.send(w.itemStack.item.id() ?: return)
    }

    override fun addPainters() {
//        super.addPainters()
    }

    fun onConfig() {

        if (ORES.isEmpty()) {
            ORES = Registry.BLOCK.filter {
                it is OreBlock
            }
        }

        val availabledOres = mc.player?.inventory?.main?.mapNotNull {
            val id = it.item.id() ?: return@mapNotNull null
            detectoreBlockByMetal(id)
        }?.flatten()?.map { block ->
            Item.fromBlock(block).defaultStack
        } ?: emptyList()

//        if(availabledOres.isEmpty()){
//            mc.currentScreen?.close()
//            return
//        }

        val root = (rootPanel as WGridPanel)
        root.setSize(256, 256)
//        val stack = mc.player?.inventory?.getStack(0)

        root.add(
            WRadialPlainPanel(256, this).apply {
                radialPlainPanel = this
                setSize(256, 256)
                addRadialBack()


                availabledOres.map {
                    createButton(it)
                }.let {
                    addRadialElement(* it.toTypedArray())
                }


            }, 0, 0, 0, 0
        )

        root.insets = Insets.NONE
    }




    fun createButton(stack: ItemStack): WRadialButton {
        val itemStack = stack.item?.defaultStack
        return WRadialButton(
            itemStack
        ).apply {

        }
    }

    fun detectoreBlockByMetal(id: Identifier): List<Block> {

        if (id.isAir()) {
            return emptyList()
        }

        var metalName = ""
        metalName = regexes.firstOrNull { pattern ->
            id.path.contains(Regex(pattern))
        }?.let { pattern ->
            id.path.replace(Regex(pattern), "")
        } ?: ""

        if (metalName.isEmpty()) {
            if (id.path in singleWords) {
                metalName = id.path
            }
        }

        if (metalName.isEmpty()) {
            return emptyList()
        }
        val oreBlockName = metalName + "_ore"
        val oreDeepslateBlockName = "deepslate_" + metalName + "_ore"


        val candidateOres = ORES.filter {
            it.id()?.namespace == id.namespace &&
                    (it.id()?.path?.contains(oreBlockName) == true || it.id()?.path?.contains(oreDeepslateBlockName) == true)
        }

        return candidateOres
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


    companion object {
        private var ORES: List<Block> = emptyList()
    }

}

