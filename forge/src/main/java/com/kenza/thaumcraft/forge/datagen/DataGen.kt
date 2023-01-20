package com.kenza.thaumcraft.forge.datagen

import net.minecraft.data.DataGenerator
import net.minecraftforge.data.event.GatherDataEvent


object DataGen {

    fun init(event: GatherDataEvent) {

        val generator: DataGenerator = event.generator
        val existingFileHelper = event.existingFileHelper

        generator.addProvider(true, BlockModels(generator, existingFileHelper))
        generator.addProvider(true, Blockstates(generator, existingFileHelper))
        generator.addProvider(true, ItemModels(generator, existingFileHelper))
//        generator.addProvider(true, ModLootTableProvider(generator))
//        generator.addProvider(true, ModBlocksStateProvider(generator, existingFileHelper))
    }
}