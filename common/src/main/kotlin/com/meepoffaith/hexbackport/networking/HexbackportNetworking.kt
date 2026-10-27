package com.meepoffaith.hexbackport.networking

import dev.architectury.networking.NetworkChannel
import com.meepoffaith.hexbackport.Hexbackport
import com.meepoffaith.hexbackport.networking.msg.HexbackportMessageCompanion

object HexbackportNetworking {
    val CHANNEL: NetworkChannel = NetworkChannel.create(Hexbackport.id("networking_channel"))

    fun init() {
        for (subclass in HexbackportMessageCompanion::class.sealedSubclasses) {
            subclass.objectInstance?.register(CHANNEL)
        }
    }
}
