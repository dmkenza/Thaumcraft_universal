package com.kenza.thaumcraft

import io.github.cottonmc.cotton.gui.impl.client.LibGuiClient
import io.kenza.support.utils.openLastWorldOnInit
import net.fabricmc.api.ClientModInitializer

//
class ThaumcraftFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        openLastWorldOnInit()
        ThaumcraftCommonClient.onInitialize()

//        ModRegistriesClient.INSTANCE.onInit();
    }
}