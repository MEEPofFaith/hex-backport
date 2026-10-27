package com.meepoffaith.hexbackport.registry

import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexContinuationTypes
import com.meepoffaith.hexbackport.casting.eval.vm.FrameBackportForEach

class HexbackportContinuationTypes : HexbackportRegistrar<ContinuationFrame.Type<*>>(
    HexRegistries.CONTINUATION_TYPE,
    { HexContinuationTypes.REGISTRY }
){
    val BACKPORT_FOREACH = make("backport_foreach", FrameBackportForEach.TYPE)

    private fun <U: ContinuationFrame, T: ContinuationFrame.Type<U>> make(name: String, continuation: T) : T{
        register(name) { continuation }
        return continuation
    }
}
