package com.kenza.thaumcraft

import com.kenza.thaumcraft.block.ArcanePedestalBlock
import com.kenza.thaumcraft.block.ArcanePedestalBlockEntity
import com.kenza.thaumcraft.client.render.ArcanePedestalBlockEntityRenderer
import com.kenza.thaumcraft.item.*
import com.kenza.thaumcraft.recipe.InfusingRecipeSerializer
import com.kenza.thaumcraft.recipe.InfusionRecipe
import com.kenza.thaumcraft.reg.*
import com.kenza.thaumcraft.reg.init.initTools
import com.kenza.thaumcraft.screen.net.ScreenNetworking
import io.kenza.support.base.BaseInitializer
import io.kenza.support.utils.*
import io.kenza.support.utils.reg.Ref.MOD_TAB
import io.kenza.support.utils.reg.Ref._MOD_ID
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.sapling.SaplingGenerator
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.FoodComponent
import net.minecraft.item.Item
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.intprovider.ConstantIntProvider
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.ConfiguredFeatures
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.TreeFeatureConfig
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.stateprovider.BlockStateProvider
import net.minecraft.world.gen.trunk.StraightTrunkPlacer

///kill @e[type=!player]
///gamerule doMobSpawning false
///gamerule doDaylightCycle false
///data get entity @s SelectedItem

//
//"greatwood_planks"
//"greatwood_log"
//"greatwood_wood"
//"greatwood_leaves"
//"stripped_greatwood_log"
//"stripped_greatwood_wood"
//
//"slab"
//"fence"
//"fence_gate"
//"stairs"
//"button"
//"plate"
//"door"
//"trapdoor"


object ThaumcraftCommon : BaseInitializer() {

    init {
        _MOD_ID = "thaumcraft"
        createRegisters()
    }

    override fun onInitialize() {

        MOD_TAB = commonPlatformHelper.registerCreativeModeTab(identifier("thaumcraft_tab")) {
            ItemTC.goggles_revealing.id().getRegSupItem().get()?.defaultStack
        }



//
//        identifier("greatwood_planks").apply {
//            val block = createBlock {
//                WOOD_SETTINGS
//            }
//            blockAndItem(block)
////            stoneMaterialDataGen()
//        }
//
//        identifier("greatwood_log").apply {
//            val block = createBlock {
//                WOOD_SETTINGS
//            }
//            blockAndItem(block)
//        }
//
//        identifier("greatwood_wood").apply {
//            val block = createBlock {
//                WOOD_SETTINGS
//            }
//            blockAndItem(block)
//        }
//
//        identifier("greatwood_planks").apply {
//            val block = createBlock {
//                WOOD_SETTINGS
//            }
//            blockAndItem(block)
//        }


        SoundTC.registerAll()
        MusicDiscTC.registerAll()
        initTools()

        ItemTC.salis_mundus.register {
            val foodComponent = FoodComponent.Builder()
                .hunger(10)
                .alwaysEdible()
                .saturationModifier(10f)
                .statusEffect(StatusEffectInstance(StatusEffects.NAUSEA, 200, 1), 0.6f)
                .build()

            SALIS_MUNDUS_ITEM = item {
                SalisMundusItem(
                    Item.Settings()
                        .maxCount(1)
                        .food(foodComponent)
                        .group(MOD_TAB)
                )
            }

            itemDataGen()
        }

        ItemTC.registerRestItems()
        BlockTC.registerAll()


        identifier("arcane_pedestal").apply {
            val block = {
                ArcanePedestalBlock(
                    this,
                    STONE_SETTINGS
                )
            }

            blockEntityType {
                BlockEntityType.Builder.create(
                    { pos: BlockPos, state: BlockState ->
                        ArcanePedestalBlockEntity(
                            this.getRegBlockEntityType(),
                            pos,
                            state
                        )
                    }, this.getRegBlock<Block>()!!.get()
                ).build(null)
            }

            ARCANE_PEDESTAL_BLOCK = blockAndItem(block)


            clientOnly {
                blockEntityTypeRender {
                    ArcanePedestalBlockEntityRenderer()
                }
            }
        }

        identifier(InfusionRecipe.INFUSUING_ID).apply {
            recipeType {
                InfusionRecipe.Type.INSTANCE
            }
            recipeSerializer {
                InfusingRecipeSerializer.INSTANCE
            }
        }


        ScreenNetworking.initServer()

        super.onInitialize()
    }


}
