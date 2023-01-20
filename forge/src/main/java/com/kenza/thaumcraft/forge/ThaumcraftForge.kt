package com.kenza.thaumcraft.forge

import com.kenza.thaumcraft.MOD_ID
import com.kenza.thaumcraft.ThaumcraftCommon
import com.kenza.thaumcraft.commonPlatformHelper
import com.kenza.thaumcraft.forge.datagen.DataGen
import dev.architectury.platform.forge.EventBuses
import io.github.cottonmc.cotton.gui.impl.LibGuiCommon
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.MOD_CONTEXT

@Mod(MOD_ID)
class ThaumcraftForge {
    init {
        commonPlatformHelper = CommonPlatformHelperForge()

        // Submit our event bus to let architectury register our content on the right time
        val bus = MOD_CONTEXT.getKEventBus();
//        val bus = FMLJavaModLoadingContext.get().modEventBus
        EventBuses.registerModEventBus(MOD_ID, bus)
        bus.addListener { event: FMLCommonSetupEvent -> setup(event) }
        bus.addListener { event: GatherDataEvent -> DataGen.init(event) }
        LibGuiCommon.onInitialize()
        ThaumcraftCommon.onInitialize()

//        ModRegistries.INSTANCE.onInit();
        MinecraftForge.EVENT_BUS.register(this)
        DistExecutor.safeRunWhenOn(Dist.CLIENT) {
            DistExecutor.SafeRunnable(
                ThaumcraftForgeClient::init
            )
        }
    }

    private fun setup(event: FMLCommonSetupEvent) {
        event.enqueueWork {}
    }

    companion object {
        val BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID)
    }
}