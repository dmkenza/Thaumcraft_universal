package com.kenza.thaumcraft.datagen

import io.kenza.support.utils.getRegBlock
import io.kenza.support.utils.identifier
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.SlabBlock
import net.minecraft.block.StairsBlock
import net.minecraft.data.client.*
import net.minecraft.data.client.BlockStateModelGenerator.createSlabBlockState
import net.minecraft.data.client.BlockStateModelGenerator.createStairsBlockState


class ModelProvider(dataGenerator: FabricDataGenerator?) : FabricModelProvider(dataGenerator) {

    val STONES_MATERIAL = listOf(
        "arcane_stone"
    )

    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator)  = with(blockStateModelGenerator){
//        blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.JUMPY_BLOCK)

        //slab
//        createSlabs(blockStateModelGenerator)

        STONES_MATERIAL.map {
            it to it + "_stairs"
        }.map {  (block, stairs) ->
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

        createSlabs()
    }

    fun BlockStateModelGenerator.createSlabs(){
        STONES_MATERIAL.map {
            it to it + "_slab"
        }.map {  (block, slab) ->
            block.getRegBlock<Block>()!!.get() to slab.getRegBlock<SlabBlock>()!!.get()
        }.forEach {  (block, slab)  ->

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
//        itemModelGenerator.register(ModItems.EIGHT_BALL, Models.GENERATED)
    }
}