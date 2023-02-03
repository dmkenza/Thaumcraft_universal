package com.kenza.thaumcraft.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.kenza.thaumcraft.MixinFields.setScreen_MinecraftClient_enabled;
import static com.kenza.thaumcraft.MixinFields.unbindAll_KeyBinding_enabled;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    //disable setScreen
    @Inject(method = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("HEAD"), cancellable = true)
    private void setScreen(@Nullable Screen screen, CallbackInfo ci) {
        if (!setScreen_MinecraftClient_enabled) {
            ci.cancel();
        }
    }
}
