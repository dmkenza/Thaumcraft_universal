package com.kenza.thaumcraft.mixin;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.kenza.thaumcraft.MixinFields.unbindAll_KeyBinding_enabled;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

//    @Redirect(
//            method = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/Vec3d;)V",
//            at = @At("INVOKE",  target = "")
//    )
//    private Vec3d tc_chagneSpeedFactor(Vec3d old) {
//         return  old.multiply( 0.5, 1, 0.5);
//    }

}
