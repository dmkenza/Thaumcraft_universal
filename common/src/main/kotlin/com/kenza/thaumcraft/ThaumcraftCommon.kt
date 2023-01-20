package com.kenza.thaumcraft

import com.google.common.base.Suppliers
import com.kenza.thaumcraft.utils.identifier
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.Registries
import io.kenza.support.utils.*
import io.kenza.support.utils.Ref.BLOCKS
import io.kenza.support.utils.Ref.BLOCK_ENTITY_TYPES
import io.kenza.support.utils.Ref.ITEMS
import io.kenza.support.utils.Ref.POINT_OF_INTEREST_TYPES
import io.kenza.support.utils.Ref.SCREEN_HANDLERS
import io.kenza.support.utils.Ref.SOUNDS_EVENTS
import io.kenza.support.utils.Ref.VILLAGER_PROFESSIONS
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Blocks
import net.minecraft.block.Material
import net.minecraft.block.SlabBlock
import net.minecraft.block.StairsBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.sound.SoundEvent
import net.minecraft.util.registry.Registry
import java.util.function.Supplier

object ThaumcraftCommon {



    init {
        ITEMS = DeferredRegister.create<Item>(MOD_ID, Registry.ITEM_KEY)
        BLOCKS = DeferredRegister.create(MOD_ID, Registry.BLOCK_KEY)
        BLOCK_ENTITY_TYPES = DeferredRegister.create(MOD_ID, Registry.BLOCK_ENTITY_TYPE_KEY)

        POINT_OF_INTEREST_TYPES = DeferredRegister.create(MOD_ID, Registry.POINT_OF_INTEREST_TYPE_KEY)
        VILLAGER_PROFESSIONS = DeferredRegister.create(MOD_ID, Registry.VILLAGER_PROFESSION_KEY)

        SOUNDS_EVENTS =
            DeferredRegister.create<SoundEvent>(MOD_ID, Registry.SOUND_EVENT_KEY)
        SCREEN_HANDLERS = DeferredRegister.create<ScreenHandlerType<*>>(MOD_ID, Registry.MENU_KEY)
    }

    val REGISTRIES: Supplier<Registries> = Suppliers.memoize {
        Registries.get(MOD_ID)
    }

    fun onInitialize() {

        REGISTRIES.get()

        var MOD_TAB = commonPlatformHelper.registerCreativeModeTab(identifier("thaumcraft_tab")) {
            Blocks.JUKEBOX.asItem().defaultStack
        }

        identifier("arcane_stone").apply {
            val block = createBlock {
                AbstractBlock.Settings
                    .of(Material.STONE)
                    .requiresTool()
                    .strength(6f)
            }

            block(block)
//            blockItem()
        }

        identifier("arcane_stone_slab").apply {

            val x1 = Supplier {
                SlabBlock(  AbstractBlock.Settings
                    .of(Material.STONE)
                    .requiresTool()
                    .strength(6f))
            }
            val x2 = block{
               x1.get()
            }
            item{
                BlockItem(x2.get(), Item.Settings().group(MOD_TAB))
            }
        }


        finishInit()
    }




    fun finishInit() {
        SOUNDS_EVENTS.register()
        BLOCKS.register()
        ITEMS.register()
        BLOCK_ENTITY_TYPES.register()
        SCREEN_HANDLERS.register()

        POINT_OF_INTEREST_TYPES.register()
        VILLAGER_PROFESSIONS.register()
    }

}
