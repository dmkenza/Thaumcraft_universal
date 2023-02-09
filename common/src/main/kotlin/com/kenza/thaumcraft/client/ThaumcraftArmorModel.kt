package com.kenza.thaumcraft.client

import com.kenza.thaumcraft.ThaumcraftArmorItem
import com.minelittlepony.MineLittlePonyServer
import com.minelittlepony.client.MineLittlePony
import com.minelittlepony.settings.PonyLevel
import io.kenza.support.utils.reg.Ref.MOD_ID
import net.minecraft.util.Identifier
import software.bernie.geckolib3.model.AnimatedGeoModel


class ThaumcraftArmorModel : AnimatedGeoModel<ThaumcraftArmorItem?>() {


    override fun getModelResource(`object`: ThaumcraftArmorItem?): Identifier {

        return when(MineLittlePony.getInstance().config.ponyLevel.get()){
            PonyLevel.PONIES -> Identifier(MOD_ID, "geo/mlp_thaumcraft_armor.geo.json")
            PonyLevel.HUMANS -> Identifier(MOD_ID, "geo/thaumcraft_armor.geo.json")
            PonyLevel.BOTH -> Identifier(MOD_ID, "geo/thaumcraft_armor.geo.json")
        }

//        return Identifier(MOD_ID, "geo/thaumcraft_armor.geo.json")
    }

    override fun getTextureResource(`object`: ThaumcraftArmorItem?): Identifier {

        return when(MineLittlePony.getInstance().config.ponyLevel.get()){
            PonyLevel.PONIES -> Identifier(MOD_ID, "textures/armor/mlp_thaumcraft_armor_texture.png")
            PonyLevel.HUMANS -> Identifier(MOD_ID, "textures/armor/thaumcraft_armor_texture.png")
            PonyLevel.BOTH -> Identifier(MOD_ID, "textures/armor/thaumcraft_armor_texture.png")
        }
//        return Identifier(MOD_ID, "textures/armor/thaumcraft_armor_texture.png")
    }

    override fun getAnimationResource(animatable: ThaumcraftArmorItem?): Identifier {
        return Identifier(MOD_ID, "animations/armor_animation.json")
    }
}