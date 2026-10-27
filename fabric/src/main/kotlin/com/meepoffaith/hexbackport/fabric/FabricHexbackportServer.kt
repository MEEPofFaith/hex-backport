package com.meepoffaith.hexbackport.fabric

import com.meepoffaith.hexbackport.Hexbackport
import net.fabricmc.api.DedicatedServerModInitializer

object FabricHexbackportServer : DedicatedServerModInitializer {
    override fun onInitializeServer() {
        Hexbackport.initServer()
    }
}
