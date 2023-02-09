package com.kenza.thaumcraft.client.render

import com.kenza.thaumcraft.block.ArcanePedestalBlockEntity
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.model.json.ModelTransformation
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Quaternion
import net.minecraft.util.math.Vec3f
import org.lwjgl.opengl.GL11

class ArcanePedestalBlockEntityRenderer : BlockEntityRenderer<ArcanePedestalBlockEntity> {

    val mc = MinecraftClient.getInstance()

    override fun render(
        entity: ArcanePedestalBlockEntity,
        tickDelta: Float, matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int,
    ) {
        matrices.push()
        val item = entity.items.firstOrNull() ?: return
//        if (item.isEmpty) {
//            return
//        }
//        GL11.glTranslatef(x.toFloat() + 0.5f, y.toFloat() + 0.75f, z.toFloat() + 0.5f)
//        GL11.glScaled(1.25, 1.25, 1.25)
//        GL11.glRotatef(ticks % 360.0f, 0.0f, 1.0f, 0.0f)

        val world = entity.world
        val gameTime = (world?.time?.toFloat() ?: 0.0f) + tickDelta
        val bob = MathHelper.sin((gameTime ) / 10.0f) * 0.2f + 0.2f

//        matrices.translate(0.5, 1.3 + (bob / 2f).toDouble(), 0.5)
        matrices.translate(0.5, 1.3 + (bob / 4f), 0.5)
        matrices.scale(1.25f, 1.25f, 1.25f)

        val spin = (gameTime) / 20.0f
        val rotation = Quaternion(Vec3f(0.0f, 1.0f, 0.0f), spin, false)
        matrices.multiply(rotation)
        mc.itemRenderer.renderItem(
            item,
            ModelTransformation.Mode.GROUND,
            light,
            overlay,
            matrices,
            vertexConsumers,
            -1000
        )
        matrices.pop()
    }
}