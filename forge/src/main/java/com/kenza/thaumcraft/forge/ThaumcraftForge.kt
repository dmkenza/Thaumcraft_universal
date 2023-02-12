package com.kenza.thaumcraft.forge

import com.kenza.thaumcraft.ThaumcraftCommon
import com.kenza.thaumcraft.commonPlatformHelper
import dev.architectury.platform.forge.EventBuses
import io.github.cottonmc.cotton.gui.impl.LibGuiCommon
import kenza.Ref.MOD_ID
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import software.bernie.geckolib3.GeckoLib
import thedarkcolour.kotlinforforge.forge.MOD_CONTEXT


@Mod("thaumcraft")
class ThaumcraftForge {
    init {

//        SoundDefinition
//        LibGuiCommon

        commonPlatformHelper = CommonPlatformHelperForge()

        // Submit our event bus to let architectury register our content on the right time
        val bus = MOD_CONTEXT.getKEventBus();
//        val bus = FMLJavaModLoadingContext.get().modEventBus
        EventBuses.registerModEventBus("thaumcraft", bus)
        bus.addListener { event: FMLCommonSetupEvent -> setup(event) }
        bus.addListener { event: FMLClientSetupEvent ->  ThaumcraftForgeClient.init(event) }
        bus.addListener { event: EntityRenderersEvent.AddLayers ->  ThaumcraftForgeClient.init(event) }
//        bus.addListener { event: RegisterKeyMappingsEvent ->  ThaumcraftForgeClient.init() }
//        bus.addListener { event: GatherDataEvent -> DataGen.init(event) }
        LibGuiCommon.onInitialize()
        ThaumcraftCommon.onInitialize()

//        ModRegistries.INSTANCE.onInit();
        GeckoLib.initialize();
        MinecraftForge.EVENT_BUS.register(this)

//        DistExecutor.safeRunWhenOn(Dist.CLIENT) {
//            DistExecutor.SafeRunnable{
//                try {
//                    ThaumcraftForgeClient.init()
//                }catch (e: Throwable){
//                    e.printStackTrace()
//                }
//
//            }
//        }



    }

    private fun setup(event: FMLCommonSetupEvent) {
        event.enqueueWork {
//            DistExecutor.safeRunWhenOn(Dist.CLIENT) {
//                DistExecutor.SafeRunnable{
//
//
//                }
//            }
        }
    }

    companion object {
        val BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID)
    }
}