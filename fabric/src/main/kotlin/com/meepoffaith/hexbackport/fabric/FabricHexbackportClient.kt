package com.meepoffaith.hexbackport.fabric

import com.meepoffaith.hexbackport.HexbackportClient
import net.fabricmc.api.ClientModInitializer

object FabricHexbackportClient : ClientModInitializer {
    override fun onInitializeClient() {
        HexbackportClient.init()
    }
}
