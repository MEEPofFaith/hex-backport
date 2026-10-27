package com.meepoffaith.hexbackport.forge

import dev.architectury.platform.forge.EventBuses
import com.meepoffaith.hexbackport.Hexbackport
import com.meepoffaith.hexbackport.forge.datagen.ForgeHexbackportDatagen
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Hexbackport.MODID)
class ForgeHexbackport {
    init {
        MOD_BUS.apply {
            EventBuses.registerModEventBus(Hexbackport.MODID, this)
            addListener(ForgeHexbackportClient::init)
            addListener(ForgeHexbackportDatagen::init)
            addListener(ForgeHexbackportServer::init)
        }
        Hexbackport.init()
    }
}
