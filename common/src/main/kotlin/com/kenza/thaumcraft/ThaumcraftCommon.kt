package com.kenza.thaumcraft

import com.google.common.base.Suppliers
import com.kenza.thaumcraft.block.ArcanePedestalBlock
import com.kenza.thaumcraft.block.ArcanePedestalBlockEntity
import com.kenza.thaumcraft.reg.STONE_SETTINGS
import com.kenza.thaumcraft.render.ArcanePedestalBlockEntityRenderer
import dev.architectury.platform.Platform
import io.kenza.support.utils.identifier
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.Registries
import io.kenza.support.utils.*
import io.kenza.support.utils.reg.Ref.BLOCKS
import io.kenza.support.utils.reg.Ref.BLOCK_ENTITY_TYPES
import io.kenza.support.utils.reg.Ref.ITEMS
import io.kenza.support.utils.reg.Ref.MOD_ID
import io.kenza.support.utils.reg.Ref.MOD_TAB
import io.kenza.support.utils.reg.Ref.POINT_OF_INTEREST_TYPES
import io.kenza.support.utils.reg.Ref.SCREEN_HANDLERS
import io.kenza.support.utils.reg.Ref.SOUNDS_EVENTS
import io.kenza.support.utils.reg.Ref.VILLAGER_PROFESSIONS
import io.kenza.support.utils.reg.Ref._MOD_ID
import net.fabricmc.api.EnvType
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.Item
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.sound.SoundEvent
import net.minecraft.util.Rarity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import java.util.function.Supplier

object ThaumcraftCommon {

    init {
        _MOD_ID = "thaumcraft"

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

    val items = listOf(
        "salis_mundus", "elemental_sword", "elemental_axe", "elemental_shovel", "elemental_pick", "elemental_hoe",
        "focus_pouch" , "goggles_revealing", "shard", "thaumonomicon", "traveller_boots"
    )

    fun onInitialize() {

        MOD_TAB = commonPlatformHelper.registerCreativeModeTab(identifier("thaumcraft_tab")) {
            Blocks.JUKEBOX.asItem().defaultStack
        }

        items.forEach {

            val itemSettings = Item.Settings()
                .maxCount(1)
                .group(MOD_TAB)

            identifier(it).apply {
                item { Item(itemSettings) }
                itemDataGen()
            }
        }

        identifier("arcane_stone").apply {
            val block = createBlock {
                STONE_SETTINGS
            }
            blockAndItem(block)
        }

        identifier("arcane_stone_slab").apply {
            val slab = {
                SlabBlock(
                    STONE_SETTINGS
                )
            }
            blockAndItem(slab)
        }

        identifier("arcane_stone_stairs").apply {
            val slab = {
                StairsBlock(
                    identifier("arcane_stone").getRegBlock<Block>()!!.get().defaultState,
                    STONE_SETTINGS
                )
            }
            blockAndItem(slab)
        }

        identifier("arcane_pedestal").apply {
//            val slab = {
//                StairsBlock(
//                    identifier("arcane_stone").getRegBlock<Block>()!!.get().defaultState,
//                    STONE_SETTINGS
//                )
//            }
            val block = {
                ArcanePedestalBlock(
                    this,
                    STONE_SETTINGS
                )
            }

            blockEntityType {
                BlockEntityType.Builder.create(
                    { pos: BlockPos, state: BlockState ->
                        ArcanePedestalBlockEntity(
                            this.getRegBlockEntityType(),
                            pos,
                            state
                        )
                    }, this.getRegBlock<Block>()!!.get()
                ).build(null)
            }

            blockAndItem(block)

            if (Platform.getEnv() == EnvType.CLIENT) {
                blockEntityTypeRender {
                    ArcanePedestalBlockEntityRenderer()
                }
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
