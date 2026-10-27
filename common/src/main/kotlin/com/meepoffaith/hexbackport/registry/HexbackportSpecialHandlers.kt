package com.meepoffaith.hexbackport.registry

import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.xplat.IXplatAbstractions

object HexbackportSpecialHandlers : HexbackportRegistrar<SpecialHandler.Factory<*>>(
    HexRegistries.SPECIAL_HANDLER,
    { IXplatAbstractions.INSTANCE.specialHandlerRegistry }
){
    private fun make(name: String, handler: SpecialHandler.Factory<*>): SpecialHandler.Factory<*> {
        register(name) { handler }
        return handler
    }
}
