package com.kenza.thaumcraft.reg

import dev.architectury.registry.registries.RegistrySupplier
import io.kenza.support.utils.getRegSupItem
import io.kenza.support.utils.identifier
import io.kenza.support.utils.item
import io.kenza.support.utils.itemDataGen
import io.kenza.support.utils.reg.DEFAULT_SINGLE_ITEM_SETTING
import net.minecraft.item.Item
import net.minecraft.util.Identifier

enum class ItemTC {
    traveller_boots, goggles_revealing,
    elemental_sword, elemental_pick, elemental_axe, elemental_hoe, elemental_shovel,
    salis_mundus, thaumonomicon, alumentum,
    shard, crystal_essence, crystal_planter,

    focus_pouch, quicksilver, rare_earth, amber, ingot_brass, ingot_thaumium, brain, discovery, knowledge_fragment,
    phial, researchnotes, resonator, sanity_soap, scribing_tools, wispy_essence, fabric;


    fun id(): Identifier {
        return identifier(this.name)
    }

    fun regSup(): RegistrySupplier<Item> {
        return id().getRegSupItem()
    }

    fun item(): Item {
        return id().getRegSupItem().get()
    }

    fun register(function: Identifier.() -> Unit) {
        function.invoke(id())
    }

    companion object {
        fun registerRestItems() {

            ItemTC.values().filter {
                kotlin.runCatching {
                    it.id().getRegSupItem()
                }.getOrNull() == null
            }.map {
                it.id().apply {
                    item { Item(DEFAULT_SINGLE_ITEM_SETTING) }
                    itemDataGen()
                }
            }


        }
    }
}