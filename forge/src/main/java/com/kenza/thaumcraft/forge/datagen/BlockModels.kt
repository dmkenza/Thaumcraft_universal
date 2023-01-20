package com.kenza.thaumcraft.forge.datagen

import com.kenza.thaumcraft.MOD_ID
import com.kenza.thaumcraft.utils.identifier
import io.kenza.support.utils.Ref
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.BlockModelProvider
import net.minecraftforge.common.data.ExistingFileHelper
import javax.annotation.Nonnull

class BlockModels(generator: DataGenerator?, existingFileHelper: ExistingFileHelper?) :

    BlockModelProvider(generator, MOD_ID, existingFileHelper) {

    override fun registerModels() {

            val name = "arcane_stone"
            val texture =  identifier("block/arcane_stone")

            slab(name + "_slab", texture, texture, texture);
			slabTop(name + "_slab_top", texture, texture, texture);

//		ArcanaDataGenerators.LIVING_WOODS.forEach((name, texture) -> {
//			cubeAll("stripped_" + name + "_wood", new ResourceLocation(texture.getNamespace(), texture.getPath().replace("block/", "block/stripped_").replace("_planks", "_wood")));
//			cubeAll(name + "_wood", new ResourceLocation(texture.getNamespace(), texture.getPath().replace("planks", "log")));
//			cubeColumn("stripped_"+name+"_log", new ResourceLocation(texture.getNamespace(), texture.getPath().replace("planks", "wood")),new ResourceLocation(texture.getNamespace(), texture.getPath().replace("planks", "log")));
//		});
//		ArcanaDataGenerators.WOODS.forEach((name, texture) -> {
//			slab(name + "_slab", texture, texture, texture);
//			slabTop(name + "_slab_top", texture, texture, texture);
//			stairs(name + "_stairs", texture, texture, texture);
//			stairsInner(name + "_stairs_inner", texture, texture, texture);
//			stairsOuter(name + "_stairs_outer", texture, texture, texture);
//			pressurePlate(name, texture);
//
//			fenceInventory(name + "_fence_inventory", texture);
//			fencePost(name + "_fence", texture);
//			fenceSide(name + "_fence", texture);
//
//			fenceGate(name + "_fence_gate", texture);
//			fenceGateOpen(name + "_fence_gate", texture);
//			fenceGateWall(name + "_fence_gate", texture);
//			fenceGateWallOpen(name + "_fence_gate", texture);
//		});
//
//		ArcanaDataGenerators.STONES.forEach((name, texture) -> {
//			cubeAll(name, texture);
//			slab(name + "_slab", texture, texture, texture);
//			slabTop(name + "_slab_top", texture, texture, texture);
//			stairs(name + "_stairs", texture, texture, texture);
//			stairsInner(name + "_stairs_inner", texture, texture, texture);
//			stairsOuter(name + "_stairs_outer", texture, texture, texture);
//			pressurePlate(name, texture);
//			wallInventory(name + "_wall_inventory", texture);
//			wallPost(name + "_wall_post", texture);
//			wallSide(name + "_wall_side", texture);
//		});
    }

    @Nonnull
    override fun getName(): String {
        return "Arcana Block Models"
    }
}