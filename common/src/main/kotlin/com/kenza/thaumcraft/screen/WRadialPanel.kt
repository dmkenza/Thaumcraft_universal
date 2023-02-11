package com.kenza.thaumcraft.screen

import com.kenza.thaumcraft.WRadialButton
import io.github.cottonmc.cotton.gui.widget.WPlainPanel
import io.github.cottonmc.cotton.gui.widget.WWidget
import io.kenza.support.utils.mc
import net.minecraft.client.util.math.MatrixStack
import kotlin.math.sqrt

class WRadialPlainPanel(
    val panelWidth: Int,
    val radialPanelListener: RadialPanelListener,
) : WPlainPanel(), RadialDelegate {

    var frames: Int = 0


    private var radialBack: WRadialBack? = null
    private var pieSlice: Float = 0.0f

    var elementsWidth = 0f
    var radialElementsWasAdded = false

    val list = ArrayList<WRadialButton>()

    // between 0-1
    var radialAnim: Float = 0f
    val selectAnim = HashMap<WRadialButton, Float>()

    var selectedWidget: WRadialButton? = null

    var radialAnimActive = true

    fun addRadialBack() {
        add(
            WRadialBack(this).apply {
//                setSize(panelWidth , panelWidth)
                radialBack = this
            }, 0, 0
        )
    }

    // angle in a circle - between 0 - 360
    fun addRadialElement(vararg ws: WRadialButton) {
        list.addAll(ws)
        selectAnim.putAll(ws.map { it to 0f })

//        w.setRadialLocation()
    }

    override fun canResize(): Boolean {
        return false
    }

    fun prepareElements() {

        if (radialElementsWasAdded) {
            return
        }
        list.map { w ->
            add(w, w.width, w.height)

            w.setOnClick {
                radialPanelListener.onElementSelected(w)
            }

            w.hoveredProperty().readOnly().addListener { property, from, to ->
                if (property.get()) {
                    selectedWidget = w
                } else {
                    selectedWidget = null
                }
            }
        }

        pieSlice = (360.0f / list.size)
        setRadialLocations()
        radialElementsWasAdded = true
    }


    override fun paint(matrices: MatrixStack?, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        super.paint(matrices, x, y, mouseX, mouseY)

//        if (frames % 2 != 0) {
//            frames++
//            return
//        }
//        frames++

        prepareElements()

        if (radialAnimActive) {
            radialAnim += 0.07f
        } else {
            radialAnim -= 0.07f
        }
        if (radialAnim > 1f) {
            radialAnim = 1f
        }

        if (radialAnim < 0f) {
            radialAnim = 0f
        }

        if (radialAnim == 0f) {
            mc.currentScreen?.close()
        }

        var selAnim = selectAnim.get(selectedWidget)?.run {
            if (radialAnim >= 1f) {
                return@run this + 0.25f
            }
            return@run this
        } ?: 0f

        if (selAnim > 1f) {
            selAnim = 1f
        }


        selectAnim.replaceAll { w, v ->
            if (selectedWidget == w && selAnim != 0f) {
                return@replaceAll selAnim
            }
            if (v <= 0) {
                return@replaceAll 0f
            }
            return@replaceAll v - 0.15f
        }


        setRadialLocations()

    }

    fun setRadialLocations() {

        elementsWidth = (20f + (list.firstOrNull()?.width?.toFloat() ?: 16f) / 3f * list.size)

        list.forEachIndexed { index, w ->
            val rad = pieSlice * index.toDouble()
            w.setRadialLocation(rad)
        }
    }

    fun WWidget.setRadialLocation(rad: Double) {


        val x0 = panelWidth / 2
        val y0 = panelWidth / 2

        val r: Float = elementsWidth
        val angle = rad - (90 * radialAnim)

        val elemAnim = selectAnim.get(this) ?: 0f
        val x = (x0 + r * Math.cos(Math.toRadians(angle)) * 0.8 * (radialAnim)) - width / 2
        val y = (y0 + r * Math.sin(Math.toRadians(angle)) * 0.8 * (radialAnim)) - height / 2

        val sizeOrigBtn = (16 * (radialAnim)).toInt()
        var sizeAnimBtn = sizeOrigBtn + (sizeOrigBtn * 0.3 * elemAnim).toInt()

        if (sizeAnimBtn < sizeOrigBtn) {
            sizeAnimBtn = sizeOrigBtn
        }
        setSize(sizeOrigBtn, sizeOrigBtn)
        (this as? WRadialButton)?.iconSize = sizeAnimBtn
        setLocation((insets.left() + x).toInt(), (insets.top() + y).toInt())


        val bx = (elementsWidth * 2).toInt()
        val by = (elementsWidth * 2).toInt()

        radialBack?.setSize(bx, by)
        radialBack?.setLocation(panelWidth / 2 - bx / 2, panelWidth / 2 - by / 2)
    }

    override fun onRadialAnimRequested(): Float = radialAnim

    fun onClose() {
        selectedWidget?.let {
            radialPanelListener.onElementSelected(it)
        }
    }

    fun radialAnimUp() {
        radialAnimActive = true
    }

    fun radialAnimDown() {
        radialAnimActive = false
    }

}

interface RadialDelegate {
    fun onRadialAnimRequested(): Float
}

interface RadialPanelListener {
    fun onElementSelected(w: WRadialButton)
}