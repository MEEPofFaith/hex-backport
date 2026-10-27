package com.meepoffaith.hexbackport

import com.meepoffaith.hexbackport.config.HexbackportClientConfig
import me.shedaniel.autoconfig.AutoConfig
import net.minecraft.client.gui.screens.Screen

object HexbackportClient {
    fun init() {
        HexbackportClientConfig.init()
    }

    fun getConfigScreen(parent: Screen): Screen {
        return AutoConfig.getConfigScreen(HexbackportClientConfig.GlobalConfig::class.java, parent).get()
    }
}
