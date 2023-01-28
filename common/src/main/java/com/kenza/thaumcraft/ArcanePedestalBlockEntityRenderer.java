package com.kenza.thaumcraft;

import com.kenza.thaumcraft.block.ArcanePedestalBlockEntity;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.Optional;

public class ArcanePedestalBlockEntityRenderer implements BlockEntityRenderer<ArcanePedestalBlockEntity> {


    @Override
    public void render(
            ArcanePedestalBlockEntity entity,
            float tickDelta, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay
    ) {
        matrices.push();

        Optional<ItemStack> item = entity.getItems().stream().findFirst();
        if(item.isEmpty()){
            return;
        }

        World world = entity.getWorld();
        float gameTime = world != null ? (float) world.getTime() : 0.0F;
        float bob = MathHelper.sin((gameTime + tickDelta) / 10.0F) * 0.2F + 0.2F;
        matrices.translate(0.5, 1.3 + (double) (bob / (float) 2), 0.5);
        float spin = (gameTime + tickDelta) / 20.0F;

        MinecraftClient mc = MinecraftClient.getInstance();
        Quaternion rotation = new Quaternion(new Vec3f(0.0F, 1.0F, 0.0F), spin, false);
        matrices.multiply(rotation);

        mc.getItemRenderer().renderItem(item.get(), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, -1000);
        matrices.pop();
    }

}
