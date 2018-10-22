@file:Suppress("UNUSED_VARIABLE", "UNUSED_VALUE", "RedundantExplicitType", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "ALWAYS_NULL", "UNNECESSARY_SAFE_CALL", "EXPERIMENTAL_FEATURE_WARNING", "MemberVisibilityCanBePrivate", "SimplifyBooleanWithConstants", "ConstantConditionIf", "MoveLambdaOutsideParentheses", "UnnecessaryVariable", "unused", "UNUSED_PARAMETER", "RemoveRedundantBackticks", "NullChecksToSafeCall", "LiftReturnOrAssignment", "ReplaceGetOrSet", "NonAsciiCharacters", "PackageName", "ClassName")

package se.lantmateriet.taco.kotlin.learn_kotlin.basics

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


/*
Sealed classes är "hybrider" mellan klasser och enums.
En subklass till en sealed klass måste ligga i samma Kotlin-fil som basklassen.

Vad ska man då med dessa till?
Tänk Verksamhetsatgard i domän Fastighet. Den får bara ha tre implementationer (TekniskAtgard, OvrigRegisteratgard och FastighetsrattsligAtgard)
men hur ska man förhindra det på ett enkelt sätt i Java?
I Kotlin skulle man göra Verksamhetsatgard till en sealed class och sedan lägga dess impl. (TekniskAtgard, OvrigRegisteratgard och FastighetsrattsligAtgard)
i samma Kotlin-fil och sedan var det bra med det.
 */

sealed class OffspringOfPeters(val name: String, val age: Int)
class Adam : OffspringOfPeters("Adam", 14)
class Felix : OffspringOfPeters("Felix", 11)


class SealedClassesTest {

    @Test
    fun `sealed classes`() {
        fun isTonaring(offspring: OffspringOfPeters): Boolean {
            return when (offspring) {
                is Adam -> true
                is Felix -> false
                else -> throw RuntimeException("Typ $offspring stöds inte")  // Behövs inte - wohoo!
            }
        }

        assertThat(isTonaring(Felix())).isFalse()
        assertThat(isTonaring(Adam())).isTrue()
    }

}