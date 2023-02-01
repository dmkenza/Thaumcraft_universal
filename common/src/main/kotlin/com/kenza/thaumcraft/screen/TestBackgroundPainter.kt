package com.kenza.thaumcraft.screen

import io.github.cottonmc.cotton.gui.client.BackgroundPainter
import io.github.cottonmc.cotton.gui.client.NinePatchBackgroundPainter
import io.github.cottonmc.cotton.gui.widget.data.Texture
import io.kenza.support.utils.identifier
import juuxel.libninepatch.NinePatch
import net.minecraft.util.Identifier


class TestBackgroundPainter  {


    companion object {
        val TEXTURE = identifier("textures/misc/radial.png")


        fun createNinePatch(texture: Identifier?): NinePatchBackgroundPainter {
            return BackgroundPainter.createNinePatch(
                Texture(texture)
            ) { builder: NinePatch.Builder<Identifier?> ->
                builder.cornerSize(
                    4
                ).cornerUv(0.25f)
            }
        }

        fun test(): NinePatchBackgroundPainter {
            return createNinePatch(TEXTURE)
        }
    }

//    override fun paintBackground(matrices: MatrixStack?, left: Int, top: Int, panel: WWidget?) {
//        RenderSystem.setShader(GameRenderer::getPositionTexShader)
//        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
//        RenderSystem.setShaderTexture(0, TEXTURE)
//
////        val x: Int = (width - backgroundWidth) / 2
////        val y: Int = (height - backgroundHeight) / 2
////        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
//
////        ScreenDrawing.texturedRect(matrices, x, y, width, height, texture, u1, v1, u2, v2, -0x1)
//    }
}