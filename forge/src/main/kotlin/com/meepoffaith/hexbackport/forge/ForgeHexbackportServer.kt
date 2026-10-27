package com.meepoffaith.hexbackport.forge

import com.meepoffaith.hexbackport.Hexbackport
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent

object ForgeHexbackportServer {
    @Suppress("UNUSED_PARAMETER")
    fun init(event: FMLDedicatedServerSetupEvent) {
        Hexbackport.initServer()
    }
}
