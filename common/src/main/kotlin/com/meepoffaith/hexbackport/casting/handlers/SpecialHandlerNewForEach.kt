package com.meepoffaith.hexbackport.casting.handlers

import at.petrak.hexcasting.api.HexAPI
import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.castables.SpecialHandler
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.eval.OperationResult
import at.petrak.hexcasting.api.casting.eval.vm.CastingImage
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.evaluatable
import at.petrak.hexcasting.api.casting.getList
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.api.casting.mishaps.MishapNotEnoughArgs
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import at.petrak.hexcasting.api.utils.lightPurple
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import at.petrak.hexcasting.xplat.IXplatAbstractions
import com.meepoffaith.hexbackport.Hexbackport
import com.meepoffaith.hexbackport.casting.eval.vm.FrameNewForEach
import com.meepoffaith.hexbackport.registry.HexbackportSpecialHandlers
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

class SpecialHandlerNewForEach(val n: Int) : SpecialHandler {
    override fun act() = InnerAction(n)

    override fun getName(): Component {
        val key = IXplatAbstractions.INSTANCE.specialHandlerRegistry.getResourceKey(HexbackportSpecialHandlers.BACKPORT_FOREACH).get()
        return HexAPI.instance().getSpecialHandlerI18nKey(key)
            .asTranslatedComponent(n).lightPurple
    }

    class InnerAction(val n: Int) : Action {
        override fun operate(env: CastingEnvironment, image: CastingImage, continuation: SpellContinuation): OperationResult {
            var stack = image.stack

            if (stack.size < 2 + n)
                throw MishapNotEnoughArgs(2 + n, stack.size)

            val datums = stack.getList(stack.lastIndex - 1, stack.size).toList()
            val instrs = evaluatable(stack[stack.lastIndex], 0)
            stack = stack.dropLast(2)

            val instrList = instrs.map({ SpellList.LList(0, listOf(it)) }, { it })

            val contextStack = stack.takeLast(n)
            val stashedStack = stack.dropLast(n)

            val frame = FrameNewForEach(datums, instrList, contextStack, stashedStack, listOf())
            val image2 = image.withUsedOp().copy(stack = listOf())

            return OperationResult(image2, listOf(), continuation.pushFrame(frame), HexEvalSounds.THOTH)
        }
    }

    class Factory : SpecialHandler.Factory<SpecialHandlerNewForEach>{
        override fun tryMatch(pattern: HexPattern, env: CastingEnvironment): SpecialHandlerNewForEach? {
            val sig = pattern.anglesSignature()
            if(sig.startsWith("waaddw")){
                val suffix = sig.substring(6)
                var tailLength = 0
                for ((index, segment) in suffix.chunked(2).withIndex()) {
                    when (index % 2) {
                        0 -> if (segment != "da") return null
                        1 -> if (segment != "ad") return null
                    }
                    tailLength++
                }

                return SpecialHandlerNewForEach(tailLength)
            }
            return null
        }

    }

    companion object{
        public val NAME: ResourceLocation = Hexbackport.id("backport_foreach")
    }
}
