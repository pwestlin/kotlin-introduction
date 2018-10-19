@file:Suppress("UNUSED_VARIABLE", "UNUSED_VALUE", "RedundantExplicitType", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "ALWAYS_NULL", "UNNECESSARY_SAFE_CALL", "EXPERIMENTAL_FEATURE_WARNING", "MemberVisibilityCanBePrivate", "SimplifyBooleanWithConstants", "ConstantConditionIf", "MoveLambdaOutsideParentheses", "UnnecessaryVariable", "unused", "UNUSED_PARAMETER", "RemoveRedundantBackticks", "NullChecksToSafeCall", "LiftReturnOrAssignment", "ReplaceGetOrSet", "NonAsciiCharacters", "PackageName", "ClassName")

package se.lantmateriet.taco.kotlin.learn_kotlin.basics

import org.junit.jupiter.api.Test

class VariablesTest {

    @Test
    fun variables() {
        // Definiera varibel av typen int
        val i: Int = 5
        // Definiera varibel av typen int mha type inference
        val ii = 5

        // En till Int med automatisk typ Int
        val j = 5

        val k = 7
        //k = 8 -> kompileringsfel eftersom k är en val som inte kan ändras

        var l = 8
        l = 9   // Detta går bra eftersom l är en var
    }


}