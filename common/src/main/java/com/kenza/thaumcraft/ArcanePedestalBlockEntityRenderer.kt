//package com.kenza.thaumcraft
//
//import com.kenza.thaumcraft.block.ArcanePedestalBlockEntity
//import net.minecraft.client.MinecraftClient
//import net.minecraft.client.render.VertexConsumerProvider
//import net.minecraft.client.render.block.entity.BlockEntityRenderer
//import net.minecraft.client.render.model.json.ModelTransformation
//import net.minecraft.client.util.math.MatrixStack
//import net.minecraft.item.ItemStack
//import net.minecraft.util.math.MathHelper
//import net.minecraft.util.math.Quaternion
//import net.minecraft.util.math.Vec3f
//import org.joml.Quaternionf
//
//open class ArcanePedestalBlockEntityRenderer : BlockEntityRenderer<ArcanePedestalBlockEntity> {
//
//    private val mc: MinecraftClient
//        get() = MinecraftClient.getInstance()
//
//    override fun render(
//        entity: ArcanePedestalBlockEntity,
//        tickDelta: Float,
//        matrices: MatrixStack,
//        vertexConsumers: VertexConsumerProvider,
//        light: Int,
//        overlay: Int,
//    ) {
//
//        matrices.push()
//
//        val item: ItemStack = entity.items.firstOrNull() ?: return
//
//        // translation above the pedestal + bobbing
//        val gameTime = (entity.getWorld()?.time?.toFloat() ?: 0f)
//        val bob = MathHelper.sin(( gameTime + tickDelta) / 10.0f) * 0.2f + 0.2f
//        matrices.translate(.5, 1.3 + bob / 2, .5)
//        // spin
//        val spin: Float = (gameTime + tickDelta) / 20.0f
//
//
//        val renderManager = MinecraftClient.getInstance().entityRenderDispatcher
//
////        val rotation: Quaternion = renderManager.camera.rotation
////        val rotation: Quaternionf = Quaternionf(0.0,1.0,0.0, 1.0).rotationY(spin)
//        val rotation: Quaternion = Quaternion(
//            Vec3f( 0.0f,1.0f, 0.0f)
//            , spin, true)
////        rotation.scale(-1.0f)
//
////        POSITIVE_Y.rotate(rotation)
//        matrices.multiply(rotation)
//
////            if (!isXAxis) matrices.multiply(POSITIVE_Y.rotationDegrees(90f))
//
////
////        matrixStack.rotate(Vector3f.rotation(spin))
//        MinecraftClient.getInstance().itemRenderer.renderItem(
//            item,
//            ModelTransformation.Mode.GROUND,
//            light,
//            overlay,
//            matrices,
//            vertexConsumers,
//            -1000
//        )
//
//        matrices.pop()
//
////        val player = mc.player ?: return
////
////        player.mainHandStack ?: return
////
////
////        val blockState = MinecraftClient.getInstance().world?.getBlockState(entity.pos) ?: return
////
////        val facing: Direction = blockState.getOrEmpty(Properties.HORIZONTAL_FACING).value ?: return
////
////        val isXAxis = facing.axis === Direction.Axis.X
////        for (i in 0..6) {
//////            val item: ItemStack = player.mainHandStack //entity.records.getStackInSlot(i)
////            val itemStack: ItemStack = entity.getDiscInSlot(i)
////
////            if (itemStack.isEmpty) continue
////
////            val discItem = (itemStack.item as? MusicDiscItem)
////
////            val slot = getShownSlot(entity, facing)
////
////            if (slot == i && discItem != null) {
////                val text = discItem.description.formatted()
////                renderText(matrices, vertexConsumers, slot, text, isXAxis, light)
////            }
////
////
////            val shiftX = if (isXAxis) .5 - .03125 else .125 + .125 * i
////            val shiftY = .375
////            val shiftZ = if (isXAxis) .125 + .125 * i else .5 + .03125
////
////            matrices.push()
////            matrices.translate(shiftX, shiftY, shiftZ)
////
//////            if (!isXAxis) matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90f))
////            if (!isXAxis) matrices.multiply(POSITIVE_Y.rotationDegrees(90f))
////
////
////            MinecraftClient.getInstance().itemRenderer.renderItem(
////                itemStack,
////                ModelTransformation.Mode.FIXED,
////                light,
////                overlay,
////                matrices,
////                vertexConsumers,
////                -1000
////            )
////
////            matrices.pop()
////
////
////        }
//    }
//
//}
