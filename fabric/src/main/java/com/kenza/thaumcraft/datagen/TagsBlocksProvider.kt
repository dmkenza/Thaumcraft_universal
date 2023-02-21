package com.kenza.thaumcraft.datagen

import com.kenza.thaumcraft.reg.ARCANE_PEDESTAL_BLOCK
import com.kenza.thaumcraft.reg.ItemTC
import io.kenza.support.utils.getRegBlock
import io.kenza.support.utils.identifier
import io.kenza.support.utils.reg.Ref.DATA_GEN_STONES_MATERIAL_NAMES
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tag.BlockTags
import net.minecraft.tag.ItemTags
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class TagsBlocksProvider(dataGenerator: FabricDataGenerator?) : FabricTagProvider.BlockTagProvider(dataGenerator) {


    override fun generateTags() {

        val stoneBlocks = DATA_GEN_STONES_MATERIAL_NAMES.map { block ->
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

class TagsItemsProvider(dataGenerator: FabricDataGenerator?) : FabricTagProvider.ItemTagProvider(dataGenerator) {

    val AXES = TagKey.of(Registry.ITEM_KEY,  identifier("axes"))
    val HOES = TagKey.of(Registry.ITEM_KEY,  identifier("hoes"))
    val PICKAXES = TagKey.of(Registry.ITEM_KEY,  identifier("pickaxes"))
    val SHOVELS = TagKey.of(Registry.ITEM_KEY,  identifier("shovels"))
    val SWORDS = TagKey.of(Registry.ITEM_KEY,  identifier("swords"))

    override fun generateTags() {
//        ItemTC.elemental_axe.id() with AXES
//        ItemTC.elemental_hoe.id() with HOES
//        ItemTC.elemental_pick.id() with PICKAXES
//        ItemTC.elemental_shovel.id() with SHOVELS
//        ItemTC.elemental_sword.id() with SWORDS
    }


    private infix fun Identifier.with(key: TagKey<Item>) {
        getOrCreateTagBuilder(key).add(
            this
        )
    }

}

