package com.kenza.thaumcraft.screen

import com.kenza.thaumcraft.Debug.x1
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WWidget
import io.kenza.support.utils.identifier
import io.kenza.support.utils.mc
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.Quaternion
import net.minecraft.util.math.Vec3f
import org.apache.logging.log4j.LogManager

class WHudTest : WWidget() {

    val TEXTURE = identifier("textures/misc/radial.png")
    val TEXTURE2 = identifier("textures/misc/radial2.png")

    var time = System.nanoTime()
    val scale = 1.1f

    override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
//        ScreenDrawing.coloredRect(matrices, x, y, width, height, -0xff0100)
        val gameTime = (mc.world?.time?.toFloat() ?: 0.0f)
        val spin = (gameTime) / 44.0f

        println("time ${time} $scale")

        renderRadial(matrices, x, y, spin, scale, this.TEXTURE)
        renderRadial(matrices, x, y, -spin, scale, this.TEXTURE2)

    }




    private fun renderRadial(matrices: MatrixStack, x: Int, y: Int, spin: Float, scale: Float, texture: Identifier) {
        matrices.push()

        val rotation = Quaternion(Vec3f(0.0f, 0.0f, 1.0f), spin, false)

        //rotate around center and scale
        matrices.apply {
            translate(((width + x) / 2.0) + x / 2, ((height + y) / 2.0) + y / 2, 0.0)
            multiply(rotation)
            scale(scale, scale, 1f)
            translate(((-(width + x) / 2.0)) - x / 2, -((height + y) / 2.0) - y / 2, 0.0)

        }
        ScreenDrawing.texturedGuiRect(matrices, x, y, width, height, texture, x1)
        matrices.pop()
    }


    override fun tick() {
//        LOGGER.debug("tick!")
    }

    companion object {
        private val LOGGER = LogManager.getLogger()
    }
}