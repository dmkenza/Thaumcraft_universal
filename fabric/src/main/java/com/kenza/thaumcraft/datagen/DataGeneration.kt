package com.kenza.thaumcraft.datagen

import com.kenza.thaumcraft.datagen.sound.MySoundDefinitionsProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class DataGeneration : DataGeneratorEntrypoint {

    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator?) {
        fabricDataGenerator?.addProvider(::RecipesProvider)
        fabricDataGenerator?.addProvider(::LangProvider)
        fabricDataGenerator?.addProvider(::LootTableGenerator);
        fabricDataGenerator?.addProvider(::ModelProvider)
        fabricDataGenerator?.addProvider(::MySoundDefinitionsProvider)
    }
}