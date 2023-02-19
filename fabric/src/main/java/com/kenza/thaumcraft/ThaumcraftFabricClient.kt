package com.kenza.thaumcraft

import com.kenza.thaumcraft.client.render.ThaumcraftArmorRenderer
import com.kenza.thaumcraft.reg.ItemTC
import io.github.cottonmc.cotton.gui.impl.client.LibGuiClient
import io.kenza.support.utils.openLastWorldOnInit
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.network.ClientPlayerEntity
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer

//
class ThaumcraftFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        openLastWorldOnInit()
        ThaumcraftCommonClient.onInitialize()

        GeoArmorRenderer.registerArmorRenderer<ClientPlayerEntity>(
            ThaumcraftArmorRenderer(),
            ItemTC.goggles_revealing.item()
        )

//        ModRegistriesClient.INSTANCE.onInit();
    }
}