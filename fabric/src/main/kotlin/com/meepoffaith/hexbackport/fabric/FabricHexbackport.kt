package com.meepoffaith.hexbackport.fabric

import com.meepoffaith.hexbackport.Hexbackport
import net.fabricmc.api.ModInitializer

object FabricHexbackport : ModInitializer {
    override fun onInitialize() {
        Hexbackport.init()
    }
}
