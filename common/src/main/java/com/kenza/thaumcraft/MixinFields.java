package com.kenza.thaumcraft;

import net.minecraft.util.hit.BlockHitResult;

public class MixinFields {

    public static boolean unbindAll_KeyBinding_enabled = true;
    public static boolean setScreen_MinecraftClient_enabled = true;

    //raycasted block before removing
    public static BlockHitResult tryBreakBlock_ServerPlayerInteractionManager_raycasted_block = null;
}
