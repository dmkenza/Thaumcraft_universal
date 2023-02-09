package com.kenza.thaumcraft

import com.kenza.thaumcraft.MixinFields.unbindAll_KeyBinding_enabled
import com.kenza.thaumcraft.client.render.ThaumcraftArmorRenderer
import com.kenza.thaumcraft.reg.GOGLES_REVEALING
import com.kenza.thaumcraft.reg.TRAVELLER_BOOTS
import com.kenza.thaumcraft.screen.ClientScreen
import com.kenza.thaumcraft.screen.RadialMenuGui
import dev.architectury.registry.client.keymappings.KeyMappingRegistry
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.kenza.support.utils.base.*
import io.kenza.support.utils.getRegBlockEntityType
import io.kenza.support.utils.mc
import io.kenza.support.utils.reg.Ref
import io.kenza.support.utils.reg.Ref.KEY_BINDINGS_MAP
import net.minecraft.block.entity.BlockEntity
import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import org.lwjgl.glfw.GLFW
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer


object ThaumcraftCommonClient : BaseModInitializer() {

    var radialMenuScreen: RadialMenuGui? = null
////        RadilMenuScreen()
////        ScrollingTestGui()
////    }a


    override fun onInitialize() {
        super.onInitialize()

        Ref.CLIENT_ENTITY_REGISTERER_MAP.map { (id, render) ->

            val type = id.getRegBlockEntityType<BlockEntity>() //as BlockEntityType<*>

            BlockEntityRendererRegistry.register(type) {
                render.get() as BlockEntityRenderer<BlockEntity>
            }
        }


        val mapping = KeyBinding(
            "key.thaumcraft.test",
            GLFW.GLFW_KEY_R,
            "category.thaumcraft"
        )
//        val radialMenuScreen = RadialMenuScreen()

//        val screen = RadialMenuScreen(
//            mapping,
////            Text.literal("RadialMenuScreen")
//        )

        GeoArmorRenderer.registerArmorRenderer<ClientPlayerEntity>(
            ThaumcraftArmorRenderer(),
            TRAVELLER_BOOTS.get(),
            GOGLES_REVEALING.get()
        )

        RadialMenuGui(mapping)

        keyBinding(mapping) {
            when (it) {
                is KeyAction.ActionDown -> {

                    if(mc.currentScreen is ClientScreen){
                        return@keyBinding
                    }

                    radialMenuScreen = RadialMenuGui(mapping)
                    unbindAll_KeyBinding_enabled = false
                    openScreen (
                        radialMenuScreen!!
                    )
                    unbindAll_KeyBinding_enabled = true
                }
                is KeyAction.ActionHold -> {
                }
                is KeyAction.ActionUp -> {
                }
                else -> {
                }
            }
        }


//        literal("config")
//            .executes(io.github.cottonmc.test.client.LibGuiTestClient.openScreen(Function<MinecraftClient, LightweightGuiDescription> { client: MinecraftClient ->
//                ConfigGui(
//                    client.currentScreen
//                )
//            }))
//        var isWasActivated = false


//        CottonHud.add( WLabel(Text.literal("Test label1")), 10, -30, 10, 10);
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
            ClientScreen(x
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


