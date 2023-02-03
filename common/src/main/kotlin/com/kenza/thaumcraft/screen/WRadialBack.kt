package com.kenza.thaumcraft.screen

import com.kenza.thaumcraft.Debug.*
import com.kenza.thaumcraft.KScreenDrawing
import dev.architectury.core.item.ArchitecturyBucketItem
import io.github.cottonmc.cotton.gui.widget.WWidget
import io.kenza.support.utils.identifier
import io.kenza.support.utils.mc
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.Quaternion
import net.minecraft.util.math.Vec3f
import org.apache.logging.log4j.LogManager

class WRadialBack (val delegate: RadialDelegate) : WWidget() {

    val TEXTURE = identifier("textures/misc/radial.png")
    val TEXTURE2 = identifier("textures/misc/radial2.png")


    override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
//        ScreenDrawing.coloredRect(matrices, x, y, width, height, -0xff0100)

        val gameTime = (mc.world?.time?.toFloat() ?: 0.0f)
        val spin = (gameTime) / 40.0f
        val radialAnim = delegate.onRadialAnimRequested()

        renderRadial(matrices, x, y, spin, radialAnim, this.TEXTURE)
        renderRadial(matrices, x, y, -spin, radialAnim, this.TEXTURE2)

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
        KScreenDrawing.texturedRect(matrices, x, y, width, height, texture, x4 , 1.0f )
        matrices.pop()
    }


    override fun tick() {
//        LOGGER.debug("tick!")
    }

    companion object {
        private val LOGGER = LogManager.getLogger()
    }
}