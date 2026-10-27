package com.meepoffaith.hexbackport

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import com.meepoffaith.hexbackport.config.HexbackportServerConfig
import com.meepoffaith.hexbackport.networking.HexbackportNetworking
import com.meepoffaith.hexbackport.registry.HexbackportActions

object Hexbackport {
    const val MODID = "hexbackport"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    @JvmStatic
    fun id(path: String) = ResourceLocation(MODID, path)

    fun init() {
        HexbackportServerConfig.init()
        initRegistries(
            HexbackportActions,
        )
        HexbackportNetworking.init()
    }

    fun initServer() {
        HexbackportServerConfig.initServer()
    }
}
