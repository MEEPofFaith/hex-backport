package com.meepoffaith.hexbackport.init

import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hexbackport.HexBackport
import com.meepoffaith.hexbackport.casting.eval.vm.FrameBackportForEach
import net.minecraft.registry.Registry

object ContinuationTypes {
    val BACKPORT_FOREACH = registerContinuation("backport_foreach", FrameBackportForEach.TYPE)

    fun init(){}

    private fun registerContinuation(name: String, continuation: ContinuationFrame.Type<*>) :  ContinuationFrame.Type<*>{
        return Registry.register(IXplatAbstractions.INSTANCE.continuationTypeRegistry, HexBackport.modLoc(name), continuation)
    }
}
