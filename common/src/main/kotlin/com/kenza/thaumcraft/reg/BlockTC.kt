package com.kenza.thaumcraft.reg

import io.kenza.support.utils.*
import net.minecraft.block.*
import net.minecraft.util.math.intprovider.ConstantIntProvider
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.stateprovider.BlockStateProvider
import net.minecraft.world.gen.trunk.StraightTrunkPlacer

object BlockTC {

    val stones = listOf(
        "arcane_stone"
    )

    val wood = listOf(
        "greatwood", "silverwood"
    )


    fun registerWood(nameWood: String) {



        identifier("${nameWood}_tree").apply {
            configuredFeature {

                val log = identifier("${nameWood}_log").getRegBlock<Block>()!!.get()
                val leaves = identifier("${nameWood}_leaves").getRegBlock<Block>()!!.get()

                val treeConfig = TreeFeatureConfig.Builder(
                    BlockStateProvider.of(log),
                    StraightTrunkPlacer(5, 6, 3),
                    BlockStateProvider.of(leaves),
                    BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 4),
                    TwoLayersFeatureSize(1, 0, 2)
                ).build()

                ConfiguredFeature(Feature.TREE, treeConfig)
            }
        }

        identifier("${nameWood}_sapling").apply {
            blockAndItem {
                SaplingBlock(
                    SaplingGenerator1("${nameWood}"),
                    AbstractBlock.Settings.copy(Blocks.OAK_SAPLING)
                )
            }
        }

        identifier("$nameWood").apply {
            woodMaterialDataGen()
        }

        identifier("${nameWood}_log").apply {
            blockAndItem {
                PillarBlock(
                    AbstractBlock.Settings.copy(Blocks.OAK_LOG)
                )
            }
        }

        identifier("${nameWood}_wood").apply {
            blockAndItem {
                PillarBlock(
                    AbstractBlock.Settings.copy(Blocks.OAK_WOOD)
                )
            }
        }

//        identifier("stripped_${nameWood}_wood").apply {
//            blockAndItem {
//                PillarBlock(
//                    AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_WOOD)
//                )
//            }
//        }
//
//        identifier("stripped_${nameWood}_log").apply {
//            blockAndItem {
//                PillarBlock(
//                    AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG)
//                )
//            }
//        }
        identifier("${nameWood}_planks").apply {
            blockAndItem {
                PillarBlock(
                    AbstractBlock.Settings.copy(Blocks.OAK_PLANKS)
                )
            }

            blockDataGen()
        }
        identifier("${nameWood}_leaves").apply {
            blockAndItem {
                PillarBlock(
                    AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)
                )
            }
            blockDataGen()
        }
    }


    fun registerStone(nameStone: String) {
        identifier(nameStone).apply {
            val block = createBlock {
                STONE_SETTINGS
            }
            blockAndItem(block)
            stoneMaterialDataGen()
        }

        identifier(nameStone + "_slab").apply {
            val slab = {
                SlabBlock(
                    STONE_SETTINGS
                )
            }
            blockAndItem(slab)
        }

        identifier(nameStone + "_stairs").apply {
            val slab = {
                StairsBlock(
                    identifier(nameStone).getRegBlock<Block>()!!.get().defaultState,
                    STONE_SETTINGS
                )
            }
            blockAndItem(slab)
        }
    }


    fun registerAll() {
        stones.forEach(::registerStone)
        wood.forEach(::registerWood)
    }
}