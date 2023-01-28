package com.kenza.thaumcraft.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class DataGeneration : DataGeneratorEntrypoint {

    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator?) {
        fabricDataGenerator?.addProvider(::LootTableGenerator);
        fabricDataGenerator?.addProvider(::ModelProvider)
    }
}