package com.meepoffaith.hexbackport.casting.eval.vm

import at.petrak.hexcasting.api.casting.SpellList
import at.petrak.hexcasting.api.casting.eval.CastResult
import at.petrak.hexcasting.api.casting.eval.ResolvedPatternType
import at.petrak.hexcasting.api.casting.eval.vm.CastingVM
import at.petrak.hexcasting.api.casting.eval.vm.ContinuationFrame
import at.petrak.hexcasting.api.casting.eval.vm.FrameEvaluate
import at.petrak.hexcasting.api.casting.eval.vm.SpellContinuation
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.utils.NBTBuilder
import at.petrak.hexcasting.api.utils.getList
import at.petrak.hexcasting.api.utils.serializeToNBT
import at.petrak.hexcasting.common.lib.hex.HexEvalSounds
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel

data class FrameNewForEach(
    val data: List<Iota>,
    val code: SpellList,
    val contextStack: List<Iota>,
    val stashedStack: List<Iota>,
    val acc: List<Iota> // Apparently TreeList exists but is... unaccesible?
) : ContinuationFrame {
    /** When halting, we add the stack state at halt to the stack accumulator, then return the stashed stack, plus the accumulator. */
    override fun breakDownwards(stack: List<Iota>): Pair<Boolean, List<Iota>> {
        val out = stashedStack.toMutableList()
        out.add(ListIota(acc))
        return true to out
    }

    /** Step the Thoth computation, enqueueing one code evaluation. */
    override fun evaluate(
        continuation: SpellContinuation,
        level: ServerLevel,
        harness: CastingVM
    ): CastResult {
        // Save the stack to the accumulator. On the first iteration, the stack will be empty.
        val newAcc = acc.toMutableList()
        newAcc.addAll(harness.image.stack)

        // If we still have data to process...
        val (newStack, newImage, newCont) = if (!data.isEmpty()) {
            // Create a stack composed of the context stack plus the next datum
            val stack = contextStack.toMutableList()
            stack.add(data.first())
            val cont2 = continuation
                // put the next Thoth object back on the stack for the next Thoth cycle,
                .pushFrame(FrameNewForEach(data.drop(1), code, contextStack, stashedStack, newAcc))
                // and prep the Thoth'd code block for evaluation.
                .pushFrame(FrameEvaluate(code, true))
            Triple(stack, harness.image.withUsedOp(), cont2)
        } else {
            // Else, restore the stashed stack, and dump our final list on top.
            val stack = stashedStack.toMutableList()
            stack.add(ListIota(newAcc))
            Triple(stack, harness.image, continuation)
        }

        return CastResult(
            ListIota(code),
            newCont,
            // reset escapes so they don't carry over to other iterations or out of thoth
            newImage.withResetEscape().copy(stack = newStack),
            listOf(),
            ResolvedPatternType.EVALUATED,
            HexEvalSounds.THOTH,
        )
    }

    override fun serializeToNBT() = NBTBuilder {
        "data" %= data.serializeToNBT()
        "code" %= code.serializeToNBT()
        "contextStack" %= contextStack.serializeToNBT()
        "stashedStack" %= stashedStack.serializeToNBT()
        "acc" %= acc.serializeToNBT()
    }

    override fun size() = data.size + code.size() + acc.size + contextStack.size + stashedStack.size

    override val type: ContinuationFrame.Type<*> = TYPE

    companion object{
        @JvmField
        val TYPE: ContinuationFrame.Type<FrameNewForEach> = object : ContinuationFrame.Type<FrameNewForEach>{
            override fun deserializeFromNBT(tag: CompoundTag, world: ServerLevel): FrameNewForEach{
                return FrameNewForEach(
                    HexIotaTypes.LIST.deserialize(tag.getList("data", Tag.TAG_COMPOUND), world)!!.list.toList(),
                    HexIotaTypes.LIST.deserialize(tag.getList("code", Tag.TAG_COMPOUND), world)!!.list,
                    HexIotaTypes.LIST.deserialize(tag.getList("contextStack", Tag.TAG_COMPOUND), world)!!.list.toList(),
                    HexIotaTypes.LIST.deserialize(tag.getList("stashedStack", Tag.TAG_COMPOUND), world)!!.list.toList(),
                    HexIotaTypes.LIST.deserialize(tag.getList("acc", Tag.TAG_COMPOUND), world)!!.list.toList(),
                )
            }
        }
    }
}
