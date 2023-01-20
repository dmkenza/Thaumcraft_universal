package com.kenza.thaumcraft

import net.fabricmc.api.ModInitializer

class ThaumcraftFabric : ModInitializer {
    override fun onInitialize() {
        commonPlatformHelper = CommonPlatformHelperFabric()
        //        ModRegistries.INSTANCE.onInit();
//        ModRegistries.INSTANCE.onSetupEvent(new Object());
//        FabricSpecificImpl.INSTANCE.initData();
    }
}