package com.kenza.thaumcraft;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public interface CommonPlatformHelper {
    public ItemGroup registerCreativeModeTab(Identifier name, Supplier<ItemStack> icon);
}
