package com.meepoffaith.hexbackport.registry

import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hexbackport.casting.handlers.SpecialHandlerBackportForEach

object HexbackportSpecialHandlers : HexbackportRegistrar<SpecialHandler.Factory<*>>(
    HexRegistries.SPECIAL_HANDLER,
    { IXplatAbstractions.INSTANCE.specialHandlerRegistry }
){
    val BACKPORT_FOREACH = make("backport_foreach", SpecialHandlerBackportForEach.Factory())

    private fun make(name: String, handler: SpecialHandler.Factory<*>): SpecialHandler.Factory<*> {
        register(name) { handler }
        return handler
    }
}
