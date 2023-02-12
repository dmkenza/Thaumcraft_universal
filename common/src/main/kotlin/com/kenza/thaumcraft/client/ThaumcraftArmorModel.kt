package com.kenza.thaumcraft.client

import com.kenza.thaumcraft.ThaumcraftArmorItem
import io.kenza.support.utils.debugMsg
import io.kenza.support.utils.reg.Ref.MOD_ID
import net.minecraft.util.Identifier
import software.bernie.geckolib3.model.AnimatedGeoModel


class ThaumcraftArmorModel : AnimatedGeoModel<ThaumcraftArmorItem?>() {

    override fun getModelResource(`object`: ThaumcraftArmorItem?): Identifier {
//        Debug.test()
        return Identifier(MOD_ID, "geo/thaumcraft_armor.geo.json")
//        return Identifier(MOD_ID, "geo/thaumcraft_armor.geo.json")
    }

    override fun getTextureResource(`object`: ThaumcraftArmorItem?): Identifier {
        return Identifier(MOD_ID, "textures/armor/thaumcraft_armor_texture.png")
    }

    override fun getAnimationResource(animatable: ThaumcraftArmorItem?): Identifier {
        return Identifier(MOD_ID, "animations/armor_animation.json")
    }
}