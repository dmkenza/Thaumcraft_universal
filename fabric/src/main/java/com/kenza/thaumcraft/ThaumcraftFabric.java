package com.kenza.thaumcraft;

import com.kenza.discholder.RefKt;
import net.fabricmc.api.ModInitializer;


public class ThaumcraftFabric implements ModInitializer {
    @Override
    public void onInitialize() {

        RefKt.commonPlatformHelper = new CommonPlatformHelperFabric();
//        ModRegistries.INSTANCE.onInit();
//        ModRegistries.INSTANCE.onSetupEvent(new Object());
//        FabricSpecificImpl.INSTANCE.initData();

    }
}
