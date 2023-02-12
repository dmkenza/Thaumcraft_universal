package com.kenza.thaumcraft.datagen

import com.kenza.thaumcraft.datagen.sound.MySoundDefinitionsProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class DataGeneration : DataGeneratorEntrypoint {

    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator?) {
        fabricDataGenerator?.addProvider(::TagsProvider);
        fabricDataGenerator?.addProvider(::LootTableProvider);
        fabricDataGenerator?.addProvider(::RecipesProvider)
        fabricDataGenerator?.addProvider(::LangProvider)
        fabricDataGenerator?.addProvider(::ModelProvider)
        fabricDataGenerator?.addProvider(::MySoundDefinitionsProvider)
    }
}