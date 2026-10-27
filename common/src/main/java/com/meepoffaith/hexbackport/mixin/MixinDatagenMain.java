package com.meepoffaith.hexbackport.mixin;

import com.meepoffaith.hexbackport.Hexbackport;
import org.spongepowered.asm.mixin.Mixin;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

// scuffed workaround for https://github.com/architectury/architectury-loom/issues/189
@Mixin({
    net.minecraft.data.Main.class,
    net.minecraft.server.Main.class,
})
public abstract class MixinDatagenMain {
    @WrapMethod(method = "main", remap = false)
    private static void hexbackport$systemExitAfterDatagenFinishes(String[] strings, Operation<Void> original) {
        try {
            original.call((Object) strings);
        } catch (Throwable throwable) {
            Hexbackport.LOGGER.error("Datagen failed!", throwable);
            System.exit(1);
        }
        Hexbackport.LOGGER.info("Datagen succeeded, terminating.");
        System.exit(0);
    }
}
