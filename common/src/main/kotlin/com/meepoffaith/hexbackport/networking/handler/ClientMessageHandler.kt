package com.meepoffaith.hexbackport.networking.handler

import dev.architectury.networking.NetworkManager.PacketContext
import com.meepoffaith.hexbackport.config.HexbackportServerConfig
import com.meepoffaith.hexbackport.networking.msg.*

fun HexbackportMessageS2C.applyOnClient(ctx: PacketContext) = ctx.queue {
    when (this) {
        is MsgSyncConfigS2C -> {
            HexbackportServerConfig.onSyncConfig(serverConfig)
        }

        // add more client-side message handlers here
    }
}
