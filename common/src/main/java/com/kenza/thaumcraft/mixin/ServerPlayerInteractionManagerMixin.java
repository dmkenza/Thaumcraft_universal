package com.kenza.thaumcraft.mixin;

import com.kenza.thaumcraft.item.ElementalPickItem;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.kenza.thaumcraft.MixinFields.tryBreakBlock_ServerPlayerInteractionManager_raycasted_block;
import static com.kenza.thaumcraft.MixinFields.unbindAll_KeyBinding_enabled;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

    @Shadow
    protected ServerWorld world;

    @Shadow
    protected ServerPlayerEntity player;

    //get raycasted block before removing
    @Inject(method = "tryBreakBlock(Lnet/minecraft/util/math/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
    private void tryBreakBlock_start(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(this.player.getMainHandStack().getItem() instanceof ElementalPickItem){
            tryBreakBlock_ServerPlayerInteractionManager_raycasted_block =
                    Item.raycast(world, player, RaycastContext.FluidHandling.NONE);
            System.out.println("test 1");

        }else{
            System.out.println("test 2");

        }

    }


    //get raycasted block before removing
    @Inject(method = "tryBreakBlock(Lnet/minecraft/util/math/BlockPos;)Z", at = @At("RETURN"), cancellable = true)
    private void tryBreakBlock_end(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        tryBreakBlock_ServerPlayerInteractionManager_raycasted_block = null;
    }
}
