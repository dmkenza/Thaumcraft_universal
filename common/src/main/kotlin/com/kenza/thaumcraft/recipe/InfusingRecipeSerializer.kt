package com.kenza.thaumcraft.recipe

import com.google.gson.JsonObject
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.collection.DefaultedList

class InfusingRecipeSerializer : RecipeSerializer<InfusionRecipe> {
    // this is the name given in the json file
    override fun read(id: Identifier, json: JsonObject): InfusionRecipe {
        val output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"))
        val ingredients = JsonHelper.getArray(json, "ingredients")
        val inputs = DefaultedList.ofSize(1, Ingredient.EMPTY)
        for (i in inputs.indices) {
            inputs[i] = Ingredient.fromJson(ingredients[i])
        }
        return InfusionRecipe(id, output, inputs)
    }

    override fun read(id: Identifier, buf: PacketByteBuf): InfusionRecipe {
        val inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY)
        for (i in inputs.indices) {
            inputs[i] = Ingredient.fromPacket(buf)
        }
        val output = buf.readItemStack()
        return InfusionRecipe(id, output, inputs)
    }

    override fun write(buf: PacketByteBuf, recipe: InfusionRecipe) {
        buf.writeInt(recipe.ingredients.size)
        for (ing in recipe.ingredients) {
            ing.write(buf)
        }
        buf.writeItemStack(recipe.output)
    }

    companion object {
        val INSTANCE = InfusingRecipeSerializer()
    }
}