package com.kenza.thaumcraft.datagen

import io.kenza.support.utils.getRegBlock
import io.kenza.support.utils.getRegItem
import io.kenza.support.utils.identifier
import io.kenza.support.utils.kotlin.isNull
import io.kenza.support.utils.reg.Ref.DATAGEN_SIMPLE_ITEMS
import io.kenza.support.utils.reg.Ref.DATA_GEN_STONES_MATERIAL_NAMES
import io.kenza.support.utils.reg.Ref.DATA_GEN_WOOD_MATERIAL_NAMES
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.SlabBlock
import net.minecraft.block.StairsBlock
import net.minecraft.client.render.RenderLayer
import net.minecraft.data.client.*
import net.minecraft.data.client.BlockStateModelGenerator.createSlabBlockState
import net.minecraft.data.client.BlockStateModelGenerator.createStairsBlockState
import net.minecraft.item.Item


class ModelProvider(dataGenerator: FabricDataGenerator?) : FabricModelProvider(dataGenerator) {


    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) =
        with(blockStateModelGenerator) {
//        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.JUMPY_BLOCK)

            //slab
//        createSlabs(blockStateModelGenerator)

//            DATA_GEN_WOOD_MATERIAL_NAMES.map { wood ->
//                ("stripped_"+ wood + "_log").getRegBlock<Block>()!!.get() to
//                        ("stripped_"+ wood + "_wood").getRegBlock<Block>()!!.get()
//            }.forEach { (log_strip, wood_strip) ->
//                registerLog(log_strip).log(log_strip).wood(wood_strip)
//            }

//            DATAGEN_SIMPLE_BLOCKS.forEach { (item, parent) ->
//                val regBlock = item.getRegBlock<Block>()!!.get()
//                registerTintableCrossBlockState(regBlock)
//            }

            DATA_GEN_WOOD_MATERIAL_NAMES.map {
                identifier(it + "_sapling")
            }.forEach { sapling ->
                sapling.apply {
                    val sapling = this.getRegBlock<Block>()!!.get()
                    registerTintableCross(
                        sapling,
                        BlockStateModelGenerator.TintType.NOT_TINTED
                    )
                }
            }



            DATA_GEN_WOOD_MATERIAL_NAMES.map { wood ->
                (wood + "_planks").getRegBlock<Block>()!!.get() to
                        (wood + "_leaves").getRegBlock<Block>()!!.get()
            }.forEach { (planks, leaves) ->
                registerSimpleCubeAll(planks)
                this.registerSingleton(leaves, TexturedModel.LEAVES)
            }

            DATA_GEN_WOOD_MATERIAL_NAMES.map { wood ->
                (wood + "_log").getRegBlock<Block>()!!.get() to
                        (wood + "_wood").getRegBlock<Block>()!!.get()
            }.forEach { (log, wood) ->
                registerLog(log).log(log).wood(wood)
            }

            createStoneStairs()
            createStoneSlabs()
        }

    fun BlockStateModelGenerator.createStoneStairs() {
        DATA_GEN_STONES_MATERIAL_NAMES.map {
            it to it + "_stairs"
        }.map { (block, stairs) ->
            block.getRegBlock<Block>()!!.get() to stairs.getRegBlock<StairsBlock>()!!.get()
        }.forEach { (block, stairs) ->
            val textureMap = TextureMap.all(block)

            val identifier1 = Models.INNER_STAIRS.upload(stairs, textureMap, modelCollector)
            val identifier2 = Models.STAIRS.upload(stairs, textureMap, modelCollector)
            val identifier3 = Models.OUTER_STAIRS.upload(stairs, textureMap, modelCollector)

            createStairsBlockState(
                stairs, identifier1, identifier2, identifier3
//                identifier("block/arcane_stone"),
//                identifier("block/arcane_stone"),
//                identifier("block/arcane_stone"),
            ).let {
                blockStateCollector.accept(it)
            }


        }
    }

    fun BlockStateModelGenerator.createStoneSlabs() {
        DATA_GEN_STONES_MATERIAL_NAMES.map {
            it to it + "_slab"
        }.map { (block, slab) ->
            block.getRegBlock<Block>()!!.get() to slab.getRegBlock<SlabBlock>()!!.get()
        }.forEach { (block, slab) ->

            val textureMap = TextureMap.all(block)

            val identifier = Models.SLAB.upload(slab, textureMap, modelCollector)
            val identifier2 = Models.SLAB_TOP.upload(slab, textureMap, modelCollector)

            createSlabBlockState(
                slab,
                identifier, identifier2, //identifier3
                identifier("block/arcane_stone"),
            ).let {
                blockStateCollector.accept(it)
            }
        }
    }


    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {

        DATAGEN_SIMPLE_ITEMS.forEach { (item, parent) ->
            val regItem = item.getRegItem<Item>()
            if (parent.isNull()) {
                itemModelGenerator.register(regItem, Models.GENERATED)
            } else {
                itemModelGenerator.register(regItem, parent)
            }
        }
//        itemModelGenerator.register(ModItems.EIGHT_BALL, Models.GENERATED)
    }
}