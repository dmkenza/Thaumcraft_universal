package com.kenza.thaumcraft.reg


import io.kenza.support.utils.identifier
import net.minecraft.block.sapling.SaplingGenerator
import net.minecraft.util.math.random.Random
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.world.gen.feature.ConfiguredFeature


class SaplingGenerator1 (val woodName: String): SaplingGenerator() {
    override fun getTreeFeature(random: Random, bees: Boolean): RegistryEntry<ConfiguredFeature<*, *>> {
        val x1 = identifier("${woodName}_tree").run {
            BuiltinRegistries.CONFIGURED_FEATURE.get(this).let { feature ->
                val rawId = BuiltinRegistries.CONFIGURED_FEATURE.getRawId(feature)
                BuiltinRegistries.CONFIGURED_FEATURE.getEntry(rawId)
            }
        }.get()


//        throw Exception("ss")
//        ConfiguredFeatures.getDefaultConfiguredFeature()
        return x1
    }
}


//
//val DOGWOOD_CHECKED: RegistryEntry<PlacedFeature> = PlacedFeatures.register(
//    "dogwood_checked",
//    ModConfiguredFeatures.DOGWOOD_TREE, List.of(PlacedFeatures.wouldSurvive(ModBlocks.DOGWOOD_SAPLING))
//)
//
//val DOGWOOD_SPAWN: RegistryEntry<ConfiguredFeature<RandomFeatureConfig, *>> = ConfiguredFeatures.register(
//    "dogwood_spawn", Feature.RANDOM_SELECTOR,
//    RandomFeatureConfig(
//        List.of(RandomFeatureEntry(DOGWOOD_CHECKED, 0.5f)),
//        DOGWOOD_CHECKED
//    )
//)