package com.kenza.thaumcraft.reg.init

import com.kenza.thaumcraft.ThaumcraftArmorItem
import com.kenza.thaumcraft.item.*
import com.kenza.thaumcraft.reg.ItemTC
import com.kenza.thaumcraft.reg.ArmorMaterialTC
import io.kenza.support.utils.item
import io.kenza.support.utils.itemDataGen
import io.kenza.support.utils.reg.Ref
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.Item
import potionstudios.byg.common.item.BYGTier

fun initTools(){

    ItemTC.goggles_revealing.register {
        item {
            ThaumcraftArmorItem(
                ArmorMaterialTC.THAUMCRAFT_DEFAULT_AM, EquipmentSlot.HEAD, Item.Settings()
                    .group(Ref.MOD_TAB)
            )
        }
        itemDataGen()
    }

    ItemTC.traveller_boots.register {
        item {
            BootsTravellerItem(
                ArmorMaterialTC.THAUMCRAFT_DEFAULT_AM, EquipmentSlot.FEET, Item.Settings()
                    .group(Ref.MOD_TAB)
            )
        }
        itemDataGen()
    }

    ItemTC.elemental_sword.register {
        item {
            ElementalSwordItem(
                BYGTier.PENDORITE, 4, -2.4f, Item.Settings()
                    .group(Ref.MOD_TAB)
            )
        }
        itemDataGen()
    }


    ItemTC.elemental_pick.register {

        item {
            ElementalPickItem(
                BYGTier.PENDORITE, 2, -2.8f, Item.Settings()
                    .group(Ref.MOD_TAB)
            )
        }
        itemDataGen()
    }

    ItemTC.elemental_hoe.register {
        item {
            ElementalHoeItem(
                BYGTier.PENDORITE, 0, 0.0f, Item.Settings()
                    .group(Ref.MOD_TAB)
            )
        }
        itemDataGen()
    }

    ItemTC.elemental_shovel.register {
        item {
            ElementalShovelItem(
                BYGTier.PENDORITE, 2.0f, -3.0f, Item.Settings()
                    .group(Ref.MOD_TAB)
            )
        }
        itemDataGen()
    }


    ItemTC.elemental_axe.register {
        item {
            ElementalAxeItem(
                BYGTier.PENDORITE, 4f, -2.4f, Item.Settings()
                    .group(Ref.MOD_TAB)
            )
        }
        itemDataGen()
    }
}