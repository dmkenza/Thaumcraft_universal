package com.kenza.thaumcraft;

import com.kenza.discholder.CommonPlatformHelper;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class CommonPlatformHelperFabric implements CommonPlatformHelper {

    public ItemGroup registerCreativeModeTab(Identifier name, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(name, icon);
    }

}
