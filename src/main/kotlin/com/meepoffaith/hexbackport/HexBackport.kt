package com.meepoffaith.hextrapats

import com.meepoffaith.hextrapats.init.*
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object HextraPats : ModInitializer {
	const val MOD_ID: String = "hextrapats"
    val LOGGER: Logger = LoggerFactory.getLogger("hextrapats")

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		LOGGER.info("Is anyone there? Hello? I'm trAPPED IN HERE AND CAN'T GET OUT H E L P ! (Now in Kotlin)")

		IotaTypes.init()
		ContinuationTypes.init()
		Patterns.init()
		SpecialHandlers.init()
		Arithmetics.init()
    }

	fun modLoc(name: String): Identifier {
		return Identifier(MOD_ID, name)
    }
}
