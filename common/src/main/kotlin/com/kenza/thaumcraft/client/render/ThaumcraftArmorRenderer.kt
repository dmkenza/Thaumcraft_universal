package com.kenza.thaumcraft.client.render

import com.kenza.thaumcraft.ThaumcraftArmorItem
import com.kenza.thaumcraft.client.ThaumcraftArmorModel
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer


class ThaumcraftArmorRenderer : GeoArmorRenderer<ThaumcraftArmorItem?>(ThaumcraftArmorModel()) {
    init {
        headBone = "armorHead"
        bodyBone = "armorBody"
        rightArmBone = "armorRightArm"
        leftArmBone = "armorLeftArm"
        rightLegBone = "armorLeftLeg"
        leftLegBone = "armorRightLeg"
        rightBootBone = "armorLeftBoot"
        leftBootBone = "armorRightBoot"
    }
}