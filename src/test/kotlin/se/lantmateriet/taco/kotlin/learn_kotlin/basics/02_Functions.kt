@file:Suppress("UNUSED_VARIABLE", "UNUSED_VALUE", "RedundantExplicitType", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "ALWAYS_NULL", "UNNECESSARY_SAFE_CALL", "EXPERIMENTAL_FEATURE_WARNING", "MemberVisibilityCanBePrivate", "SimplifyBooleanWithConstants", "ConstantConditionIf", "MoveLambdaOutsideParentheses", "UnnecessaryVariable", "unused", "UNUSED_PARAMETER", "RemoveRedundantBackticks", "NullChecksToSafeCall", "LiftReturnOrAssignment", "ReplaceGetOrSet", "NonAsciiCharacters", "PackageName", "ClassName")

package se.lantmateriet.taco.kotlin.learn_kotlin.basics

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FunctionsTest {

    @Test
    fun `ja, en funktion kan faktiskt namnges så här`() {
        // ...men bara i tester är detta acceptabelt!
    }

    @Test
    // Funktion med specad returtyp och "body"
    fun `function with return type and body`() {
        // Japp, man kan ha funktioner inuti en funktion - de kallas local functions
        fun double(int: Int): Int {
            return int * 2
        }

        assertThat(double(3)).isEqualTo(6)
    }

    @Test
    fun `function with expression body and automatic return type`() {
        fun square(int: Int) = int * int

        assertThat(square(3)).isEqualTo(9)
    }

    @Test
    fun `no return type`() {
        fun voidFunctionOrWhat() {
            1 + 1
        }

        // Returtypen void saknas, utan kallas Unit och kan utelämnas vid deklaration.
        assertThat(voidFunctionOrWhat()).isInstanceOf(Unit::class.java)
    }

    @Test
    fun varargs() {
        fun concat(vararg strings: String): String {
            return strings.joinToString(" ")
        }

        assertThat(concat("Varargs", "can", "be", "useful")).isEqualTo("Varargs can be useful")
    }

    @Test
    fun defaultValuesForFunctionParams() {
        fun multiply(a: Int, b: Int = 2) = a * b

        assertThat(multiply(5, 5)).isEqualTo(25)
        assertThat(multiply(5)).isEqualTo(10)

        // Jfr med Javas overloaded methods som anropar varandra
    }

    @Test
    fun functionDefaultAndNamedArgs() {
        fun reformat(string: String, reverse: Boolean = false, upperCase: Boolean = false): String {
            var formatted = string
            if (reverse) {
                formatted = formatted.reversed()
            }
            if (upperCase) {
                formatted = formatted.toUpperCase()
            }

            return formatted
        }

        assertThat(reformat("Elsa i Paris")).isEqualTo("Elsa i Paris")
        assertThat(reformat("Elsa i Paris", true)).isEqualTo("siraP i aslE")
        assertThat(reformat("Elsa i Paris", true, true)).isEqualTo("SIRAP I ASLE")
        assertThat(reformat("Elsa i Paris", upperCase = true)).isEqualTo("ELSA I PARIS")
    }

}