package com.kenza.thaumcraft.datagen

import com.kenza.thaumcraft.reg.ARCANE_PEDESTAL_BLOCK
import io.kenza.support.utils.getRegBlock
import io.kenza.support.utils.reg.Ref
import io.kenza.support.utils.reg.Ref.BLOCKS
import io.kenza.support.utils.reg.Ref.STONES_MATERIALS
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.block.Block
import net.minecraft.tag.BlockTags

class TagsProvider(dataGenerator: FabricDataGenerator?) : FabricTagProvider.BlockTagProvider(dataGenerator) {


    override fun generateTags() {

        val stoneBlocks = STONES_MATERIALS.map { block ->
            listOf<String>(
                block,
                block + "_stairs",
                block + "_slab"
            )
        }.flatten().map {
            it.getRegBlock<Block>()!!.get()
        } + ARCANE_PEDESTAL_BLOCK.get()

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(
            * stoneBlocks.toTypedArray()
        )

    }
}