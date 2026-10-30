package com.meepoffaith.hexbackport.init

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.castables.OperationAction
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.hex.HexActions
import com.meepoffaith.hexbackport.HexBackport
import net.minecraft.registry.Registry

object Patterns {
    fun init(){
    }

    private fun register(name: String, signature: String, startDir: HexDir, action: Action){
        Registry.register(HexActions.REGISTRY, HexBackport.modLoc(name),
            ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action)
        )
    }

    private fun register(name: String, signature: String, startDir: HexDir): HexPattern {
        val pattern = HexPattern.fromAngles(signature, startDir)
        Registry.register(HexActions.REGISTRY, HexBackport.modLoc(name),
            ActionRegistryEntry(pattern, OperationAction(pattern))
        )
        return pattern
    }
}
