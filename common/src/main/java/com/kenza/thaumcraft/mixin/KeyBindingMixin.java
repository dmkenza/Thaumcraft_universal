package com.kenza.thaumcraft.mixin;

import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.kenza.thaumcraft.MixinFields.unbindAll_KeyBinding_enabled;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin {

    @Inject(method = "unpressAll()V", at = @At("HEAD"), cancellable = true)
    private static void unbindAll(CallbackInfo ci) {
        if (!unbindAll_KeyBinding_enabled) {
            ci.cancel();
        }
    }

}
