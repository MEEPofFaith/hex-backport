@file:JvmName("HexbackportAbstractions")

package com.meepoffaith.hexbackport

import dev.architectury.injectables.annotations.ExpectPlatform
import com.meepoffaith.hexbackport.registry.HexbackportRegistrar

fun initRegistries(vararg registries: HexbackportRegistrar<*>) {
    for (registry in registries) {
        initRegistry(registry)
    }
}

@ExpectPlatform
fun <T : Any> initRegistry(registrar: HexbackportRegistrar<T>) {
    throw AssertionError()
}
