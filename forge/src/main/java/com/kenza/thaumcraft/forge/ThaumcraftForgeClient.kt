package com.kenza.thaumcraft.forge

import com.kenza.thaumcraft.ThaumcraftArmorItem
import com.kenza.thaumcraft.ThaumcraftCommonClient
import com.kenza.thaumcraft.client.ThaumcraftArmorModel
import com.kenza.thaumcraft.client.render.ThaumcraftArmorRenderer
import io.github.cottonmc.cotton.gui.impl.client.LibGuiClient
import io.kenza.support.utils.debugMsg
import io.kenza.support.utils.openLastWorldOnInit
import io.kenza.support.utils.reg.Ref
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.ArmorMaterial
import net.minecraft.util.Identifier
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import software.bernie.geckolib3.item.GeoArmorItem
import software.bernie.geckolib3.model.AnimatedGeoModel
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer

//
//
//@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
//@Mod.EventBusSubscriber(modid = MOD_ID, bus = MOD)
object ThaumcraftForgeClient {
    //    @OnlyIn(Dist.DEDICATED_SERVER)

//    class GeoArmorItemTest(materialIn: ArmorMaterial?, slot: EquipmentSlot?, builder: Settings?):
//        GeoArmorItem(materialIn, slot, builder)

    fun init(event: EntityRenderersEvent.AddLayers) {

    }


    fun init(event: FMLClientSetupEvent) {

        GeoArmorRenderer.registerArmorRenderer( ThaumcraftArmorItem::class.java) {
            ThaumcraftArmorRenderer()
        }

        LibGuiClient.onInitializeClient()
        openLastWorldOnInit()
        ThaumcraftCommonClient.onInitialize()
//        ModRegistriesClient.INSTANCE.onInit();
    }

}