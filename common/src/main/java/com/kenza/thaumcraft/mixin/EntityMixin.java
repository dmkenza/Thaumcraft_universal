package com.kenza.thaumcraft.mixin;

import com.kenza.thaumcraft.utils.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.kenza.thaumcraft.MixinFields.unbindAll_KeyBinding_enabled;
import static com.kenza.thaumcraft.utils.EntityUtil.TRAVEL_BOOTS_JUMP_FACTOR;
import static com.kenza.thaumcraft.utils.EntityUtil.TRAVEL_BOOTS_SPEED_FACTOR;

@Mixin(Entity.class)
public class EntityMixin {

//    @Redirect(
//            method = "Lnet/minecraft/entity/LivingEntity;travel(Lnet/minecraft/util/math/Vec3d;)V",
//            at = @At("INVOKE",  target = "")
//    )
//    private Vec3d tc_chagneSpeedFactor(Vec3d old) {
//         return  old.multiply( 0.5, 1, 0.5);
//    }

    @Shadow
    public float stepHeight;

    @Shadow
    public float fallDistance;

    public Float oldStepHeight;


    @ModifyVariable(method = "Lnet/minecraft/entity/Entity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V", at = @At("HEAD"), ordinal = 0)
    private Vec3d tc_addSpeedFactor(Vec3d move) {
        if (EntityUtil.shouldIncreaseSpeedFactor(this)) {
            if(oldStepHeight == null){
                oldStepHeight = stepHeight;
            }
            stepHeight = oldStepHeight + 0.4f;

            if (fallDistance > 0.0F) {
                fallDistance -= 0.25F;
            }

            return move.multiply(TRAVEL_BOOTS_SPEED_FACTOR, 1, TRAVEL_BOOTS_SPEED_FACTOR);
        }
        if(oldStepHeight != null){
            stepHeight = oldStepHeight;
            oldStepHeight = null;
        }
        return move;
    }

//    @ModifyVariable(method = "Lnet/minecraft/entity/Entity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V", at = @At("HEAD"), ordinal = 0)
//    private Vec3d tc_addSpeedFactor2(Vec3d move) {
//        return move.multiply(1.5, 1, 1.5);
//    }

    @Inject(method = "Lnet/minecraft/entity/Entity;getJumpVelocityMultiplier()F", at = @At("RETURN"), cancellable = true)
    private void tc_addJumpFactor(CallbackInfoReturnable<Float> cir) {
        if (EntityUtil.shouldIncreaseJumpFactor(this)) {
            cir.setReturnValue(cir.getReturnValue() * TRAVEL_BOOTS_JUMP_FACTOR);
        }else{
            cir.setReturnValue(cir.getReturnValue());
        }
    }


}
