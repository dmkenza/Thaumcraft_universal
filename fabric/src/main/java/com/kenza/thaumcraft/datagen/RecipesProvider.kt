package com.kenza.thaumcraft.datagen

import io.kenza.support.utils.getRegBlock
import io.kenza.support.utils.reg.Ref.DATA_GEN_STONES_MATERIAL_NAMES
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.block.Block
import net.minecraft.block.StairsBlock
import net.minecraft.data.server.recipe.RecipeJsonProvider
import net.minecraft.recipe.Ingredient
import java.util.function.Consumer
import io.kenza.support.utils.kotlin.to
import net.minecraft.block.SlabBlock
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.predicate.item.ItemPredicate

class RecipesProvider(dataGenerator: FabricDataGenerator?) : FabricRecipeProvider(dataGenerator) {

//    val STONES_MATERIALS = Ref.DATAGEN_STONE_MATERIALS.map {
//        it.path
//    }

    override fun generateRecipes(exporter: Consumer<RecipeJsonProvider>) {

        DATA_GEN_STONES_MATERIAL_NAMES.map {
            it to it + "_stairs"
        }.map { (block, stairs) ->
            block to block.getRegBlock<Block>()!!.get() to stairs.getRegBlock<StairsBlock>()!!.get()
        }.forEach { (materialName, block, stairs) ->


            createStairsRecipe(
                stairs,
                Ingredient.ofItems(
                    block
                )
            )
                .addDefaultCriterion(materialName, block.asItem())
                .offerTo(exporter)


        }

        DATA_GEN_STONES_MATERIAL_NAMES.map {
            it to it + "_slab"
        }.map { (block, slab) ->
            block to block.getRegBlock<Block>()!!.get() to slab.getRegBlock<SlabBlock>()!!.get()
        }.forEach { (materialName, block, slab) ->
            createSlabRecipe(
                slab, Ingredient.ofItems(block)
            ).addDefaultCriterion(materialName, block.asItem())
                .offerTo(exporter)
        }


    }


}

fun CraftingRecipeJsonBuilder.addDefaultCriterion(name: String, item: Item): CraftingRecipeJsonBuilder {
    return criterion(
        "has_${name}", net.minecraft.advancement.criterion.InventoryChangedCriterion.Conditions.items(
            ItemPredicate(
                null,
                mutableSetOf(item),
                net.minecraft.predicate.NumberRange.IntRange.ANY,
                net.minecraft.predicate.NumberRange.IntRange.ANY,
                net.minecraft.predicate.item.EnchantmentPredicate.ARRAY_OF_ANY,
                net.minecraft.predicate.item.EnchantmentPredicate.ARRAY_OF_ANY,
                null,
                net.minecraft.predicate.NbtPredicate.ANY
            )
        )
    )

}
