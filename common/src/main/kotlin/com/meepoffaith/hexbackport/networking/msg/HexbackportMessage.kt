package com.meepoffaith.hexbackport.networking.msg

import dev.architectury.networking.NetworkChannel
import dev.architectury.networking.NetworkManager.PacketContext
import com.meepoffaith.hexbackport.Hexbackport
import com.meepoffaith.hexbackport.networking.HexbackportNetworking
import com.meepoffaith.hexbackport.networking.handler.applyOnClient
import com.meepoffaith.hexbackport.networking.handler.applyOnServer
import net.fabricmc.api.EnvType
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.server.level.ServerPlayer
import java.util.function.Supplier

sealed interface HexbackportMessage

sealed interface HexbackportMessageC2S : HexbackportMessage {
    fun sendToServer() {
        HexbackportNetworking.CHANNEL.sendToServer(this)
    }
}

sealed interface HexbackportMessageS2C : HexbackportMessage {
    fun sendToPlayer(player: ServerPlayer) {
        HexbackportNetworking.CHANNEL.sendToPlayer(player, this)
    }

    fun sendToPlayers(players: Iterable<ServerPlayer>) {
        HexbackportNetworking.CHANNEL.sendToPlayers(players, this)
    }
}

sealed interface HexbackportMessageCompanion<T : HexbackportMessage> {
    val type: Class<T>

    fun decode(buf: FriendlyByteBuf): T

    fun T.encode(buf: FriendlyByteBuf)

    fun apply(msg: T, supplier: Supplier<PacketContext>) {
        val ctx = supplier.get()
        when (ctx.env) {
            EnvType.SERVER, null -> {
                Hexbackport.LOGGER.debug("Server received packet from {}: {}", ctx.player.name.string, this)
                when (msg) {
                    is HexbackportMessageC2S -> msg.applyOnServer(ctx)
                    else -> Hexbackport.LOGGER.warn("Message not handled on server: {}", msg::class)
                }
            }
            EnvType.CLIENT -> {
                Hexbackport.LOGGER.debug("Client received packet: {}", this)
                when (msg) {
                    is HexbackportMessageS2C -> msg.applyOnClient(ctx)
                    else -> Hexbackport.LOGGER.warn("Message not handled on client: {}", msg::class)
                }
            }
        }
    }

    fun register(channel: NetworkChannel) {
        channel.register(type, { msg, buf -> msg.encode(buf) }, ::decode, ::apply)
    }
}
