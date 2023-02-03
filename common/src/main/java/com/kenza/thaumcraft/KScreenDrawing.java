package com.kenza.thaumcraft;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class KScreenDrawing {

    public static void texturedRect(MatrixStack matrices, int x, int y, int width, int height, Identifier texture, int color, float opacity) {
        texturedRect(matrices, x, y, width, height, texture, 0, 0, 1, 1, color, opacity);
    }

    public static void texturedRect(MatrixStack matrices, int x, int y, int width, int height, Texture texture, int color, float opacity) {
        texturedRect(matrices, x, y, width, height, texture.image(), texture.u1(), texture.v1(), texture.u2(), texture.v2(), color, opacity);
    }

    public static void texturedRect(MatrixStack matrices, int x, int y, int width, int height, Identifier texture, float u1, float v1, float u2, float v2, int color, float opacity) {
        if (width <= 0) width = 1;
        if (height <= 0) height = 1;

        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        Matrix4f model = matrices.peek().getPositionMatrix();
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(opacity, opacity, opacity, opacity);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        buffer.vertex(model, x,         y + height, 0).texture(u1, v2).next();
        buffer.vertex(model, x + width, y + height, 0).texture(u2, v2).next();
        buffer.vertex(model, x + width, y,          0).texture(u2, v1).next();
        buffer.vertex(model, x,         y,          0).texture(u1, v1).next();
        BufferRenderer.drawWithShader(buffer.end());
        RenderSystem.disableBlend();
    }
}
