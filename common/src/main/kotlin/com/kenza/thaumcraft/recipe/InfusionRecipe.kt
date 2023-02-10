package com.kenza.thaumcraft.recipe

import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.*
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class InfusionRecipe (val valueId: Identifier,val  valueOutput: ItemStack, val recipeItems: DefaultedList<Ingredient>): Recipe<SimpleInventory> {

    private val catalyst: Ingredient? = null

    override fun matches(inventory: SimpleInventory, world: World): Boolean {
        return recipeItems.get(0).test(inventory.getStack(0))
    }

    override fun craft(inventory: SimpleInventory?): ItemStack {
        return output
    }

    override fun fits(width: Int, height: Int): Boolean {
        return true
    }

    override fun getOutput(): ItemStack {
        return valueOutput.copy()
    }

    override fun getId(): Identifier {
        return valueId
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return InfusingRecipeSerializer.INSTANCE
    }

    override fun getType(): RecipeType<*> {
        return Type.INSTANCE
    }


    class Type : RecipeType<InfusionRecipe?> {
        companion object {
            val INSTANCE: Type = Type()
        }

    }

    companion object {
        val INFUSUING_ID = "infusing"
    }

}