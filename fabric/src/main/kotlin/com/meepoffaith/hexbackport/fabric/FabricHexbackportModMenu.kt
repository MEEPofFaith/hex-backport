package com.meepoffaith.hexbackport.fabric

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import com.meepoffaith.hexbackport.HexbackportClient

object FabricHexbackportModMenu : ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory(HexbackportClient::getConfigScreen)
}
