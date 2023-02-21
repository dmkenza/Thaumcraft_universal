package com.kenza.thaumcraft

import com.kenza.thaumcraft.MixinFields.unbindAll_KeyBinding_enabled
import com.kenza.thaumcraft.item.ElementalPickItem
import com.kenza.thaumcraft.screen.ClientScreen
import com.kenza.thaumcraft.screen.InGameHud
import com.kenza.thaumcraft.screen.RadialMenuGui
import com.kenza.thaumcraft.screen.net.ScreenNetworking
import dev.architectury.registry.client.keymappings.KeyMappingRegistry
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry
import io.github.cottonmc.cotton.gui.client.CottonHud
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.kenza.support.base.BaseClientInitializer
import io.kenza.support.utils.keys.KeyAction
import io.kenza.support.utils.keys.KeyActionReceiver
import io.kenza.support.utils.base.*
import io.kenza.support.utils.getRegBlock
import io.kenza.support.utils.getRegBlockEntityType
import io.kenza.support.utils.identifier
import io.kenza.support.utils.keys.ActionBinder
import io.kenza.support.utils.kotlin.safeCast
import io.kenza.support.utils.mc
import io.kenza.support.utils.reg.Ref
import io.kenza.support.utils.reg.Ref.DATA_GEN_WOOD_MATERIAL_NAMES
import io.kenza.support.utils.reg.Ref.KEY_BINDINGS_MAP
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import org.lwjgl.glfw.GLFW


object ThaumcraftCommonClient : BaseClientInitializer() {

    var radialMenuScreen: RadialMenuGui? = null
////        RadilMenuScreen()
////        ScrollingTestGui()
////    }a

    var inGameHud: InGameHud? = null


    override fun onInitialize() {
        super.onInitialize()

        DATA_GEN_WOOD_MATERIAL_NAMES.map {
            identifier(it + "_leaves").getRegBlock<Block>() to
            identifier(it + "_sapling").getRegBlock<Block>()
        }.forEach { (leaves, sapling) ->
            BlockRenderLayerMap.INSTANCE.putBlock(leaves!!.get(), RenderLayer.getCutout());
            BlockRenderLayerMap.INSTANCE.putBlock(sapling!!.get(), RenderLayer.getCutout());
        }




        Ref.CLIENT_ENTITY_REGISTERER_MAP.map { (id, render) ->

            val type = id.getRegBlockEntityType<BlockEntity>() //as BlockEntityType<*>

            BlockEntityRendererRegistry.register(type) {
                render.get() as BlockEntityRenderer<BlockEntity>
            }
        }


//        Ref.CLIENT_ENTITY_REGISTERER_MAP.map { (id, render) ->
//
//            val type = id.getRegBlockEntityType<BlockEntity>() //as BlockEntityType<*>
//
//            BlockEntityRendererRegistry.register(type) {
//                render.get() as BlockEntityRenderer<BlockEntity>
//            }
//        }


        val mappingMain = KeyBinding(
            "key.thaumcraft.main",
            GLFW.GLFW_KEY_G,
            "category.thaumcraft"
        )

//        val mappingTest = KeyBinding(
//            "key.thaumcraft.test",
//            GLFW.GLFW_KEY_V,
//            "category.thaumcraft"
//        )

//        val radialMenuScreen = RadialMenuScreen()

//        val screen = RadialMenuScreen(
//            mapping,
////            Text.literal("RadialMenuScreen")
//        )

        inGameHud = InGameHud()
        CottonHud.add(inGameHud, 20, -28, 24, 24)


//        inGameHud = InGameHud()
//        CottonHud.add( inGameHud, 20, -54, 40, 40)


//        RadialMenuGui(mappingMain)

        keyBinding(mappingMain) {
            when (it) {
                is KeyAction.ActionDown -> {

//                    CottonHud.remove(inGameHud)
//                    inGameHud = InGameHud()
//                    CottonHud.add( inGameHud, 20, -28, 24, 24)


                    if (mc.currentScreen is ClientScreen) {
                        return@keyBinding
                    }

                    mc.player?.mainHandStack?.item.safeCast<ElementalPickItem>().apply {
                        this ?: return@apply

                        if (mc.player?.isInSneakingPose == true) {
                            ScreenNetworking.send(identifier(""))
                            return@keyBinding
                        }

                        radialMenuScreen = RadialMenuGui(mappingMain)
                        unbindAll_KeyBinding_enabled = false
                        openScreen(
                            radialMenuScreen!!
                        )
                        unbindAll_KeyBinding_enabled = true
                    }


                }

                is KeyAction.ActionHold -> {
                }

                is KeyAction.ActionUp -> {
                }

                else -> {
                }
            }
        }

        GlobalScope.launch {
            RadialMenuGui(mappingMain)
        }


//        literal("config")
//            .executes(io.github.cottonmc.test.client.LibGuiTestClient.openScreen(Function<MinecraftClient, LightweightGuiDescription> { client: MinecraftClient ->
//                ConfigGui(
//                    client.currentScreen
//                )
//            }))
//        var isWasActivated = false


//        CottonHud.add( WLabel(Text.literal("Test label1")), 10, -30, 10, 10);


        try {
            ScreenNetworking.initClient()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun keyBinding(mapping: KeyBinding, receiver: KeyActionReceiver) {
        KeyMappingRegistry.register(mapping)
        KEY_BINDINGS_MAP.put(
            mapping,
            ActionBinder.ActionBinderHolder(mapping, receiver)
        )
    }

    private fun openScreen(x: LightweightGuiDescription) {
        val client = mc
        client.setScreen(
            ClientScreen(
                x
//                screenFactory.apply(
//                    client
//                )
            )
        )
    }

//    private fun openScreen(screenFactory: Function<MinecraftClient, LightweightGuiDescription>): Command<ClientCommandSource> {
//        return Command { context: CommandContext<ClientCommandSource> ->
//            val client = mc
//
//            client.setScreen(
//                CottonClientScreen(
//                    screenFactory.apply(
//                        client
//                    )
//                )
//            )
//
//            client.send(Runnable {
//
//            })
//            Command.SINGLE_SUCCESS
//        }
//    }
}


