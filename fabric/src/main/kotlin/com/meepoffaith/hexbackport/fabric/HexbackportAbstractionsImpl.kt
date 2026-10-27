@file:JvmName("HexbackportAbstractionsImpl")

package com.meepoffaith.hexbackport.fabric

import com.meepoffaith.hexbackport.registry.HexbackportRegistrar
import net.minecraft.core.Registry

fun <T : Any> initRegistry(registrar: HexbackportRegistrar<T>) {
    val registry = registrar.registry
    registrar.init { id, value -> Registry.register(registry, id, value) }
}
