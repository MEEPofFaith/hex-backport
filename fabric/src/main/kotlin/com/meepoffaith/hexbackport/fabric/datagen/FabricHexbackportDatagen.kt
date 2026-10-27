package com.meepoffaith.hexbackport.fabric.datagen

import com.meepoffaith.hexbackport.datagen.HexbackportActionTags
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object FabricHexbackportDatagen : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        val pack = gen.createPack()

        pack.addProvider(::HexbackportActionTags)
    }
}
