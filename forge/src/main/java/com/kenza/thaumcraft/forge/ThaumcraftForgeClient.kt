package com.kenza.thaumcraft.forge

import com.kenza.thaumcraft.ThaumcraftCommonClient
import com.kenza.thaumcraft.ThaumcraftCommonClient.onInitialize
import io.github.cottonmc.cotton.gui.impl.client.LibGuiClient
import io.kenza.support.utils.literal
import io.kenza.support.utils.openLastWorldOnInit

//
//
//@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
//@Mod.EventBusSubscriber(modid = MOD_ID, bus = MOD)
object ThaumcraftForgeClient {
    //    @OnlyIn(Dist.DEDICATED_SERVER)
    fun init() {

        val x1 = literal("sd")
        LibGuiClient.onInitializeClient()
        openLastWorldOnInit()
        ThaumcraftCommonClient.onInitialize()


//        ModRegistriesClient.INSTANCE.onInit();
    }
}