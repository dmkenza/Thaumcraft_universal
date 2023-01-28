package com.kenza.thaumcraft.forge;

import io.github.cottonmc.cotton.gui.impl.client.LibGuiClient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
//

//
//@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
//@Mod.EventBusSubscriber(modid = MOD_ID, bus = MOD)
public class ThaumcraftForgeClient {

//    @OnlyIn(Dist.DEDICATED_SERVER)
    public static void init() {
        LibGuiClient.onInitializeClient();

//        ModRegistriesClient.INSTANCE.onInit();
    }


}


