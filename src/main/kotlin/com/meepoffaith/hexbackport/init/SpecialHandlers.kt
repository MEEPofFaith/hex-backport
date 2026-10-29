package com.meepoffaith.hexbackport.init

import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hexbackport.HexBackport
import com.meepoffaith.hexbackport.casting.handlers.SpecialHandlerBackportForEach
import net.minecraft.registry.Registry

object SpecialHandlers {
    val BACKPORT_FOREACH = registerSpecialHandler("backport_foreach", SpecialHandlerBackportForEach.Factory())

    fun init(){}

    private fun registerSpecialHandler(name: String, handler: SpecialHandler.Factory<*>) : SpecialHandler.Factory<*>{
        Registry.register(IXplatAbstractions.INSTANCE.specialHandlerRegistry, HexBackport.modLoc(name), handler)
        return handler
    }
}
