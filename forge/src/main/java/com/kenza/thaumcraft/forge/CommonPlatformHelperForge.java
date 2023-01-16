package com.kenza.thaumcraft.forge;

import com.kenza.thaumcraft.CommonPlatformHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class CommonPlatformHelperForge implements CommonPlatformHelper {

    public ItemGroup registerCreativeModeTab(Identifier name, Supplier<ItemStack> icon) {
        return new ItemGroup(name.getNamespace()) {
            @Override
            public ItemStack createIcon() {
                return icon.get();
            }
        };
    }


}
