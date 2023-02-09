package com.kenza.thaumcraft.reg

import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemConvertible
import net.minecraft.recipe.Ingredient
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Lazy
import potionstudios.byg.common.item.BYGArmorMaterial
import potionstudios.byg.common.item.BYGItems
import java.util.function.Supplier

class TArmorMaterials private constructor(
    private val name: String,
    private val durabilityMultiplier: Int,
    private val protectionAmounts: IntArray,
    private val enchantability: Int,
    private val equipSound: SoundEvent,
    private val toughness: Float,
    private val knockbackResistance: Float,
    repairIngredientSupplier: Supplier<Ingredient>,
) : ArmorMaterial {
    private val repairIngredientSupplier: Lazy<Ingredient>
    override fun getDurability(slot: EquipmentSlot): Int {
        return MAX_DAMAGE_ARRAY[slot.entitySlotId] * durabilityMultiplier
    }

    override fun getProtectionAmount(slot: EquipmentSlot): Int {
        return protectionAmounts[slot.entitySlotId]
    }

    override fun getEnchantability(): Int {
        return enchantability
    }

    override fun getEquipSound(): SoundEvent {
        return equipSound
    }

    override fun getRepairIngredient(): Ingredient {
        return repairIngredientSupplier.get()
    }

    override fun getName(): String {
        return name
    }

    override fun getToughness(): Float {
        return toughness
    }

    override fun getKnockbackResistance(): Float {
        return knockbackResistance
    }

    init {
        this.repairIngredientSupplier = Lazy(repairIngredientSupplier)
    }

    companion object {

        //    MYTHRIL("mythril", 16, new int[]{2, 5, 7, 2}, 28,
        //            SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0f, 0.0f, () -> Ingredient.ofItems(ModItems.MYTHRIL_INGOT));
        private val MAX_DAMAGE_ARRAY: IntArray

//        val THAUMCRAFT_RESEARCHER_AM = createArmorMaterial ("thaumcraft_researcher")
        val THAUMCRAFT_DEFAULT_AM = createArmorMaterial ("arcane")

        fun createArmorMaterial(name: String) = BYGArmorMaterial(
            name,
            39,
            intArrayOf(4, 7, 9, 4),
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            3.2f
        ) {
            Ingredient.ofItems(
                *arrayOf(
                    BYGItems.AMETRINE_GEMS.get() as ItemConvertible
                )
            )
        }


        //            val PENDORITE = BYGArmorMaterial(
//                "pendorite",
//                15,
//                intArrayOf(1, 4, 5, 2),
//                12,
//                SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
//                0.0f
//            ) {
//                Ingredient.ofItems(
//                    *arrayOf(
//                        BYGItems.PENDORITE_SCRAPS.get() as ItemConvertible
//                    )
//                )
//            }
        init {
            MAX_DAMAGE_ARRAY = intArrayOf(13, 15, 16, 11)
        }
    }
}