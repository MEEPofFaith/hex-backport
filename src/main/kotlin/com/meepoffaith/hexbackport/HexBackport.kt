package com.meepoffaith.hexbackport

import com.meepoffaith.hexbackport.init.ContinuationTypes
import com.meepoffaith.hexbackport.init.Patterns
import com.meepoffaith.hexbackport.init.SpecialHandlers
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HexBackport : ModInitializer {
	const val MOD_ID: String = "hexbackport"
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ContinuationTypes.init()
		Patterns.init()
		SpecialHandlers.init()
    }

	fun modLoc(name: String): Identifier {
		return Identifier(MOD_ID, name)
    }
}
