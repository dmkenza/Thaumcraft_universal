package com.kenza.thaumcraft.screen.net

import com.kenza.thaumcraft.screen.interfaces.RadialPanelHandleable
import dev.architectury.networking.NetworkManager
import io.github.cottonmc.cotton.gui.impl.ScreenNetworkingImpl
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.kenza.support.utils.debugMsg
import io.kenza.support.utils.identifier
import io.kenza.support.utils.isRenderThread
import io.kenza.support.utils.kotlin.safeCast
import io.netty.buffer.Unpooled
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.world.World
import java.util.concurrent.Executor

object ScreenNetworking {

    val SCREEN_MESSAGE_S2C = identifier("radial_menu_select_item_s2c")
    val SCREEN_MESSAGE_C2S = identifier("radial_menu_select_item_c2s")

    fun initServer() {

        NetworkManager.registerReceiver(
            NetworkManager.Side.C2S, SCREEN_MESSAGE_C2S
        ) { buf: PacketByteBuf?, context: NetworkManager.PacketContext ->
            val buf = buf ?: return@registerReceiver
            val server = context.player.server ?: return@registerReceiver

            handle(
                server,
                context.player,
                buf
            )
        }
    }


    fun initClient() {
        NetworkManager.registerReceiver(
            NetworkManager.Side.S2C, SCREEN_MESSAGE_S2C
        ) { buf: PacketByteBuf?, context: NetworkManager.PacketContext ->
            val buf = buf ?: return@registerReceiver
            handle(
                MinecraftClient.getInstance(),
                context.player,
                buf
            )
        }
    }

    fun send(message: Identifier) {
        val buf = PacketByteBufs.create();
        buf.writeIdentifier(message)
        val netPackerId = if (!isRenderThread()) SCREEN_MESSAGE_S2C else SCREEN_MESSAGE_C2S

        NetworkManager.sendToServer(netPackerId, buf);
    }

    private fun handle(executor: Executor, player: PlayerEntity, buf: PacketByteBuf) {
        val messageId: Identifier = buf.readIdentifier()
        player.mainHandStack.item.safeCast<RadialPanelHandleable>()?.apply {
            onRadialPanelItemSelected(messageId, player)
        }

    }

}