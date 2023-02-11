package com.kenza.thaumcraft

import com.turtlearmymc.doublejump.Doublejump
import io.github.cottonmc.cotton.gui.impl.LibGuiCommon
import net.fabricmc.api.ModInitializer

class ThaumcraftFabric : ModInitializer {
    override fun onInitialize() {
        commonPlatformHelper = CommonPlatformHelperFabric()

        ThaumcraftCommon.onInitialize()

        //        ModRegistries.INSTANCE.onInit();
//        ModRegistries.INSTANCE.onSetupEvent(new Object());
//        FabricSpecificImpl.INSTANCE.initData();
    }
}