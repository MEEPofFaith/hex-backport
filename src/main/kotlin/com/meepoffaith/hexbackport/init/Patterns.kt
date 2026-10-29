package com.meepoffaith.hextrapats.init

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.castables.OperationAction
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.hex.HexActions
import com.meepoffaith.hextrapats.HextraPats
import com.meepoffaith.hextrapats.casting.actions.NoConsOperationAction
import com.meepoffaith.hextrapats.casting.actions.eval.OpConditionalEval
import com.meepoffaith.hextrapats.casting.actions.eval.OpConditionalHalt
import com.meepoffaith.hextrapats.casting.actions.eval.OpForEachIndex
import com.meepoffaith.hextrapats.casting.actions.eval.OpMainForEach
import com.meepoffaith.hextrapats.casting.actions.lists.*
import com.meepoffaith.hextrapats.casting.actions.logic.OpNoConsBoolCoerce
import com.meepoffaith.hextrapats.casting.actions.logic.OpNoConsEquality
import com.meepoffaith.hextrapats.casting.actions.math.OpDegRad
import com.meepoffaith.hextrapats.casting.actions.math.OpRadDeg
import com.meepoffaith.hextrapats.casting.actions.math.OpRandRange
import com.meepoffaith.hextrapats.casting.actions.math.OpRandZero
import com.meepoffaith.hextrapats.casting.actions.nullary.OpNullCoalesce
import com.meepoffaith.hextrapats.casting.actions.nullary.OpNullExecute
import com.meepoffaith.hextrapats.casting.actions.queryentity.OpEntityBodyYaw
import com.meepoffaith.hextrapats.casting.actions.queryentity.OpEntityLookAngles
import com.meepoffaith.hextrapats.casting.actions.raycast.OpOutlineAxisRaycast
import com.meepoffaith.hextrapats.casting.actions.raycast.OpOutlineRaycast
import com.meepoffaith.hextrapats.casting.actions.sets.*
import com.meepoffaith.hextrapats.casting.actions.stack.OpFloat
import com.meepoffaith.hextrapats.casting.actions.stack.OpSink
import com.meepoffaith.hextrapats.casting.actions.vecmanip.OpVecList
import com.meepoffaith.hextrapats.casting.actions.vecmath.OpFromPolar
import com.meepoffaith.hextrapats.casting.actions.vecmath.OpRandVec
import com.meepoffaith.hextrapats.casting.actions.vecmath.OpToPolar
import com.meepoffaith.hextrapats.casting.actions.vecmath.OpVecDist
import net.minecraft.registry.Registry
import net.minecraft.util.math.Vec3d

object Patterns {
    val ROT_ABOUT_X = register("rot_about/x", "aaqqqqqea", HexDir.SOUTH_WEST)
    val ROT_ABOUT_Y = register("rot_about/y", "aaqqqqqew", HexDir.SOUTH_WEST)
    val ROT_ABOUT_Z = register("rot_about/z", "aaqqqqqed", HexDir.SOUTH_WEST)
    val CONSTRUCT_ABOUT_X = register("cons_about/x", "daqqqqqea", HexDir.NORTH_WEST)
    val CONSTRUCT_ABOUT_Y = register("cons_about/y", "daqqqqqew", HexDir.NORTH_WEST)
    val CONSTRUCT_ABOUT_Z = register("cons_about/z", "daqqqqqed", HexDir.NORTH_WEST)
    val NORMALIZE = register("normalize", "eeeeedww", HexDir.SOUTH_WEST)
    val LEN_EQ = register("len_eq", "adqqaqw", HexDir.EAST)
    val LEN_NEQ = register("len_neq", "daeedew", HexDir.EAST)
    val IN_RANGE = register("in_range", "qqqq", HexDir.SOUTH_WEST)
    val OUT_RANGE = register("out_range", "eaae", HexDir.SOUTH_EAST)
    val INVERT = register("invert", "waqawqa", HexDir.SOUTH_WEST)
    val INCREMENT = register("increment", "waawawaaw", HexDir.NORTH_EAST)
    val DECREMENT = register("decrement", "wddwdwddw", HexDir.NORTH_WEST)
    val APPROACH = register("approach", "dedqadeeed", HexDir.SOUTH_WEST)
    val ANGLE_DIST = register("angle_dist", "awdaqqqqqea", HexDir.NORTH_EAST)
    val ANGLE_APPROACH = register("angle_approach", "awdaqqqqqwd", HexDir.NORTH_EAST)
    val SET_INSERT_RET = register("set_insert_ret", "edqdewd", HexDir.SOUTH_WEST )
    val SET_REMOVE_RET = register("set_remove_ret", "edqdewaqaaed", HexDir.SOUTH_WEST)
    val VEC_GET_X = register("vec/get/x", "qeeeeedwwa", HexDir.EAST)
    val VEC_GET_Y = register("vec/get/y", "qeeeeedwww", HexDir.EAST)
    val VEC_GET_Z = register("vec/get/z", "qeeeeedwwd", HexDir.EAST)
    val VEC_SET_X = register("vec/set/x", "eqqqqqawwa", HexDir.EAST)
    val VEC_SET_Y = register("vec/set/y", "eqqqqqawww", HexDir.EAST)
    val VEC_SET_Z = register("vec/set/z", "eqqqqqawwd", HexDir.EAST)
    val VEC_LEN = register("vec/set/len", "qqqqqawqaqw", HexDir.EAST)
    val ROUND_INT = register("round/int", "qdwae", HexDir.EAST)
    val ROUND_EXACT = register("round/exact", "eawdq", HexDir.EAST)

    fun init(){
        register("deg_to_rad", "qqqqqdwdq", HexDir.WEST, OpDegRad)
        register("rad_to_deg", "qdwdqqqqq", HexDir.NORTH_EAST, OpRadDeg)
        register("rand_zero", "dedqeqqq", HexDir.EAST, OpRandZero)
        register("rand_range", "eeeqeqqq", HexDir.SOUTH_WEST, OpRandRange)

        register("rand_vec", "eeeeeqeqqq", HexDir.EAST, OpRandVec)
        register("vec_dist", "aqqqqqeqeeeeed", HexDir.EAST, OpVecDist)
        register("from_polar", "eqqadaqa", HexDir.EAST, OpFromPolar)
        register("to_polar", "qedadeed", HexDir.EAST, OpToPolar)
        register("swap_vec_list", "qqqqqdeee", HexDir.WEST, OpVecList)

        //Come on, Elise!
        register("haha_ha_one", "qqqqqeq", HexDir.NORTH_WEST, Action.makeConstantOp(Vec3Iota(Vec3d(1.0, 1.0, 1.0))))
        register("eno_ah_ahah", "eeeeeqq", HexDir.SOUTH_WEST, Action.makeConstantOp(Vec3Iota(Vec3d(-1.0, -1.0, -1.0))))

        register("peek/front", "aaqwqaaq", HexDir.SOUTH_WEST, OpPeek(false))
        register("peek/back", "qaeaqe", HexDir.NORTH_WEST, OpPeek(true))
        register("peek/index", "deeedew", HexDir.NORTH_WEST, OpPeekAt)
        register("split_list", "wdedqqa", HexDir.EAST, OpSplitList)
        register("del_element/first", "dedwqaeaqa", HexDir.NORTH_EAST, OpDelete)
        register("del_element/all", "dedwqaeaqaw", HexDir.NORTH_EAST, OpDeleteAll)
        register("swindle_list", "dqdeqaawddea", HexDir.WEST, OpListSwindle)
        register("scronglwfijspoivjqwofklcrvewb", "ddedqdaqwdwaqawdwqaqww", HexDir.EAST, OpShuffle)

        register("nocon/bool_coerce", "ddaw", HexDir.SOUTH_EAST, OpNoConsBoolCoerce)
        registerNoConsumeOp("nocon/greater", "ddwe", HexDir.WEST, Arithmetic.GREATER, 2)
        registerNoConsumeOp("nocon/less", "ddeq", HexDir.WEST, Arithmetic.LESS, 2)
        registerNoConsumeOp("nocon/greater_eq", "ddwee", HexDir.WEST, Arithmetic.GREATER_EQ, 2)
        registerNoConsumeOp("nocon/less_eq", "ddeqq", HexDir.WEST, Arithmetic.LESS_EQ, 2)
        registerNoConsumeOp("nocon/len_eq", "ddqadqqaqw", HexDir.WEST, LEN_EQ, 2)
        registerNoConsumeOp("nocon/len_neq", "ddqdaeedew", HexDir.WEST, LEN_NEQ, 2)
        register("nocon/eq", "ddqad", HexDir.WEST, OpNoConsEquality(false))
        register("nocon/neq", "ddqda", HexDir.WEST, OpNoConsEquality(true))

        register("empty_set/num", "eedqddeeaqaa", HexDir.NORTH_WEST, OpEmptyNumSet)
        register("empty_set/vec", "eedqddeeqqqqq", HexDir.NORTH_WEST, OpEmptyVecSet)
        register("empty_set/entity", "eedqddeweaqa", HexDir.NORTH_WEST, OpEmptyEntitySet)
        register("last_n_set", "ewdwaawaqde", HexDir.SOUTH_WEST, OpLastNToSet)
        register("splat_set", "qwawddwdeaq", HexDir.NORTH_WEST, OpSplatSet)
        register("set_to_list", "eedqddeqaaeaqq", HexDir.NORTH_WEST, OpSetToList)
        register("list_to_set", "qqaeaadwaddqdee", HexDir.NORTH_EAST, OpListToSet)

        register("true_eval", "deaqqaaqa", HexDir.SOUTH_EAST, OpConditionalEval(true))
        register("false_eval", "deaqqdded", HexDir.SOUTH_EAST, OpConditionalEval(false))
        register("true_halt", "aqdeedded", HexDir.SOUTH_WEST, OpConditionalHalt(true))
        register("false_halt", "aqdeeaaqa", HexDir.SOUTH_WEST, OpConditionalHalt(false))
        register("index_for_each", "dadaddqdq", HexDir.NORTH_EAST, OpForEachIndex)
        register("main_for_each", "aawdadad", HexDir.WEST, OpMainForEach(false))
        register("main_index_for_each", "aawdadaddqdq", HexDir.WEST, OpMainForEach(true))

        register("nulls/coalesce", "wawaa", HexDir.SOUTH_EAST, OpNullCoalesce)
        register("nulls/exec", "wawaadq", HexDir.SOUTH_EAST, OpNullExecute)

        register("capsizing", "ddadeq", HexDir.WEST, OpSink(false))
        register("capsizing/copy", "aadaqe", HexDir.EAST, OpSink(true))
        register("dredging", "ddadaq", HexDir.WEST, OpFloat(false))
        register("dredging/copy", "aadade", HexDir.EAST, OpFloat(true))

        register("raycast/outline", "wqaaeaa", HexDir.EAST, OpOutlineRaycast)
        register("raycast/outaxis", "weddqdd", HexDir.EAST, OpOutlineAxisRaycast)
        register("entity/look_angles", "waa", HexDir.EAST, OpEntityLookAngles)
        register("entity/body_yaw", "wdd", HexDir.EAST, OpEntityBodyYaw)
    }

    private fun register(name: String, signature: String, startDir: HexDir, action: Action){
        Registry.register(HexActions.REGISTRY, HextraPats.modLoc(name),
            ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), action)
        )
    }

    private fun register(name: String, signature: String, startDir: HexDir): HexPattern {
        val pattern = HexPattern.fromAngles(signature, startDir)
        Registry.register(HexActions.REGISTRY, HextraPats.modLoc(name),
            ActionRegistryEntry(pattern, OperationAction(pattern))
        )
        return pattern
    }

    private fun registerNoConsumeOp(name: String, signature: String, startDir: HexDir, copied: HexPattern, argc: Int){
        val pattern = HexPattern.fromAngles(signature, startDir)
        Registry.register(HexActions.REGISTRY, HextraPats.modLoc(name),
            ActionRegistryEntry(pattern, NoConsOperationAction(copied, argc))
        )
    }
}
