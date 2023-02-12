package com.kenza.thaumcraft.datagen

import io.kenza.support.utils.id
import io.kenza.support.utils.reg.Ref.BLOCKS
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Blocks
import net.minecraft.loot.LootTable
import net.minecraft.util.Identifier
import java.util.function.BiConsumer

class LootTableProvider(dataGenerator: FabricDataGenerator?) :
    FabricBlockLootTableProvider(dataGenerator) {

//    private val loot_tables: List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> =
//        ImmutableList.of(Pair.of({ ModBlockLootTables() }, LootContextParamSets.BLOCK),
//            Pair.of({ ModChestLootTables() }, LootContextParamSets.CHEST)
//        )

//    override fun accept(t: BiConsumer<Identifier, LootTable.Builder>) {
//
////        val stoneBlocks = STONES_MATERIALS.map { block ->
////            listOf(
////                block, block + "_slab", block + "_stairs"
////            )
////        }.flatten().map {
////            it.getRegBlock<Block>()
////        }
//
//
////
//
//
//    }

    override fun generateBlockLootTables() {

        BLOCKS.map {
            it.get()
        }.forEach {
            addDrop(it)
        }
    }



}

