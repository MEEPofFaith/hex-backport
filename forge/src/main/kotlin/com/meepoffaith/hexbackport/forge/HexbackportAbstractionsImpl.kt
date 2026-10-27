@file:JvmName("HexbackportAbstractionsImpl")

package com.meepoffaith.hexbackport.forge

import com.meepoffaith.hexbackport.registry.HexbackportRegistrar
import net.minecraftforge.registries.RegisterEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

fun <T : Any> initRegistry(registrar: HexbackportRegistrar<T>) {
    MOD_BUS.addListener { event: RegisterEvent ->
        event.register(registrar.registryKey) { helper ->
            registrar.init(helper::register)
        }
    }
}
