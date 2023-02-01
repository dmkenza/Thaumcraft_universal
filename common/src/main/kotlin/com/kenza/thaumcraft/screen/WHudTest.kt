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

    override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
//        ScreenDrawing.coloredRect(matrices, x, y, width, height, -0xff0100)
        val gameTime = (mc.world?.time?.toFloat() ?: 0.0f)
        val spin = (gameTime) / 40.0f

        renderRadial(matrices, x, y, spin, this.TEXTURE)
        renderRadial(matrices, x, y, -spin, this.TEXTURE2)

    }


    fun renderRadial(matrices: MatrixStack, x: Int, y: Int, spin: Float, texture: Identifier) {
        matrices.push()
        //rotate around center
        matrices.rotateAroundCenter(x, y, spin)

        ScreenDrawing.texturedGuiRect(matrices, x, y, width, height, texture, x1)
        matrices.pop()
    }

    private fun MatrixStack.rotateAroundCenter(x: Int, y: Int, spin: Float) {
        val rotation = Quaternion(Vec3f(0.0f, 0.0f, 1.0f), spin, false)
        translate(((width + x) / 2.0) + x / 2, ((height + y) / 2.0) + y / 2, 0.0)
        multiply(rotation)
        translate(((-(width + x) / 2.0)) - x / 2, -((height + y) / 2.0) - y / 2, 0.0)

    }


    override fun tick() {
//        LOGGER.debug("tick!")
    }

    companion object {
        private val LOGGER = LogManager.getLogger()
    }
}