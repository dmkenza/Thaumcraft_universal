package com.kenza.thaumcraft.datagen

import io.kenza.support.utils.base.gson
import io.kenza.support.utils.id
import io.kenza.support.utils.kotlin.safeCast
import io.kenza.support.utils.reg.Ref.ITEMS
import io.kenza.support.utils.reg.Ref.MOD_ID
import io.kenza.support.utils.reg.Ref.MOD_TAB
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.text.MutableText
import net.minecraft.text.TranslatableTextContent
import org.apache.commons.lang3.text.WordUtils
import java.io.File

class LangProvider(dataGenerator: FabricDataGenerator) : FabricLanguageProvider(dataGenerator) {

    val path =
        dataGenerator.output.parent.toString() + "\\resources\\assets\\${MOD_ID}\\lang\\en_us.json"

    override fun generateTranslations(translationBuilder: TranslationBuilder?) {

        val textJson = File(path).readText()
        val elementsToExclude = runCatching {
            gson.fromJson(textJson, MutableMap::class.java) ?: emptyMap()
        }.getOrNull() ?: emptyMap()

        val transKey = MOD_TAB.displayName.content.safeCast<TranslatableTextContent>()?.key

        if (!elementsToExclude.containsKey(transKey) && transKey != null) {
            var value = WordUtils.capitalizeFully(transKey.replace("_", " "));
            translationBuilder?.add(transKey, value);
        }

        ITEMS.forEach { item ->
            if (!elementsToExclude.containsKey(item.get().translationKey)) {
                var value = WordUtils.capitalizeFully(item.id.path.replace("_", " "));
                translationBuilder?.add(item.get(), value);
            }
        }
    }
}