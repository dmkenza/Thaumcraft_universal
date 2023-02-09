package com.kenza.thaumcraft

import com.google.common.base.Suppliers
import com.kenza.thaumcraft.block.ArcanePedestalBlock
import com.kenza.thaumcraft.block.ArcanePedestalBlockEntity
import com.kenza.thaumcraft.client.render.ArcanePedestalBlockEntityRenderer
import com.kenza.thaumcraft.item.*
import com.kenza.thaumcraft.reg.*
import com.kenza.thaumcraft.reg.TArmorMaterials.Companion.THAUMCRAFT_DEFAULT_AM
import dev.architectury.registry.registries.DeferredRegister
import dev.architectury.registry.registries.Registries
import io.kenza.support.utils.*
import io.kenza.support.utils.reg.DEFAULT_SINGLE_ITEM_SETTING
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
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ArmorItem
import net.minecraft.item.FoodComponent
import net.minecraft.item.Item
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import potionstudios.byg.common.item.BYGTier
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
        "focus_pouch", "shard", "thaumonomicon"
    )

    fun onInitialize() {


        MOD_TAB = commonPlatformHelper.registerCreativeModeTab(identifier("thaumcraft_tab")) {
            Blocks.JUKEBOX.asItem().defaultStack
        }


        SoundFX.values().map {
            identifier(it.name).apply {
                soundEvent()
            }
        }

//        identifier("traveller_boots").apply {
//            TRAVELLER_BOOTS = item {
//                ArmorItem(
//                    THAUMCRAFT_DEFAULT_AM, EquipmentSlot.FEET, Item.Settings()
//                        .group(MOD_TAB)
//                )
//            }
//            itemDataGen()
//        }

        identifier("goggles_revealing").apply {
            GOGLES_REVEALING = item {
                ThaumcraftArmorItem(
                    THAUMCRAFT_DEFAULT_AM, EquipmentSlot.HEAD, Item.Settings()
                        .group(MOD_TAB)
                )
            }
            itemDataGen()
        }

        identifier("elemental_hoe").apply {
            ELEMENTAL_HOE = item {
                ElementalHoeItem(
                    BYGTier.PENDORITE, 0, 0.0f, Item.Settings()
                        .group(MOD_TAB)
                )
            }
            itemDataGen()
        }

        identifier("elemental_shovel").apply {
            ELEMENTAL_SHOVEL = item {
                ElementalShovelItem(
                    BYGTier.PENDORITE, 2.0f, -3.0f, Item.Settings()
                        .group(MOD_TAB)
                )
            }
            itemDataGen()
        }


        identifier("elemental_axe").apply {
            ELEMENTAL_AXE = item {
                ElementalAxeItem(
                    BYGTier.PENDORITE, 4f, -2.4f, Item.Settings()
                        .group(MOD_TAB)
                )
            }
            itemDataGen()
        }

        identifier("elemental_sword").apply {
            ELEMENTAL_SWORD = item {
                ElementalSwordItem(
                    BYGTier.PENDORITE, 4, -2.4f, Item.Settings()
                        .group(MOD_TAB)
                )
            }
            itemDataGen()
        }


        identifier("elemental_pick").apply {

            ELEMENTAL_PICK = item {
                ElementalPickItem(
                    BYGTier.PENDORITE, 2, -2.8f, Item.Settings()
                        .group(MOD_TAB)
                )
            }
            itemDataGen()
        }

        identifier("salis_mundus").apply {

            val foodComponent = FoodComponent.Builder()
                .hunger(10)
                .alwaysEdible()
                .saturationModifier(10f)
                .statusEffect(StatusEffectInstance(StatusEffects.NAUSEA, 200, 1), 0.6f)
                .build()

            SALIS_MUNDUS_ITEM = item {
                SalisMundusItem(
                    Item.Settings()
                        .maxCount(1)
                        .food(foodComponent)
                        .group(MOD_TAB)
                )
            }

            itemDataGen()

        }

        items.forEach {
            identifier(it).apply {
                item { Item(DEFAULT_SINGLE_ITEM_SETTING) }
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

            clientRun {
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
