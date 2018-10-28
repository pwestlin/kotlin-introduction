@file:Suppress("PackageName", "RemoveRedundantBackticks", "unused", "UNUSED_PARAMETER", "UNUSED_VARIABLE", "UnnecessaryVariable")

package se.lantmateriet.taco.kotlin.learnkotlin.lessbasic

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


abstract class Parent(val name: String) {
    fun sayParentStuff() {
        println("Clean your room!")
    }
}

class Child(name: String) : Parent(name) {
    fun sayChildStuff() {
        println("No!")
    }
}

data class DataParent(val name: String)
//class DataChild(name: String) : DataParent(name)    // Går inte för DataParent är final (by default)
class DataChild(name: String) : Parent(name)

class DataChildWithCopyFunction(@JsonProperty("name") name: String) : Parent(name) {

    fun copy(): DataChildWithCopyFunction {
        val mapper = ObjectMapper()
        val str = mapper.writeValueAsString(this)
        val obj = mapper.readValue(str, DataChildWithCopyFunction::class.java)

        return obj
    }
}


class InheritenceTest {

    @Test
    fun `child`() {
        val child = Child("Felix")
    }

    @Test
    fun `dataChild`() {
        val parent = DataParent("Felix")
        parent.copy()

        val child = DataChild("Felix")
        //assertThat(child.copy())      // copy() finns inte när en data class ärver från en annan klass
    }

    @Test
    fun `DataChildWithCopyFunction`() {
        val child = DataChildWithCopyFunction("Adam")
        println("child = ${child}")
        val child2 = child.copy()
        println("child2 = ${child2}")

        assertThat(child2 == child).isFalse()
        assertThat(child2.name).isEqualTo(child.name)
    }
}

class Objektidentitet(@JsonProperty("uuid") uuid: String)

abstract class Utbytesobjekt2(val objektidentitet: Objektidentitet, val objektversion: Int)

class Fastighet2(
    @JsonProperty("objektidentitet") objektidentitet: Objektidentitet,
    @JsonProperty("objektversion") objektversion: Int = 1,
    @JsonProperty("status") val status: String) : Utbytesobjekt2(objektidentitet, objektversion) {
    fun copy(): Fastighet2 {
        val mapper = ObjectMapper()
        val str = mapper.writeValueAsString(this)
        val obj = mapper.readValue(str, Fastighet2::class.java)

        return obj
    }

}

class ObjektidentitetTest {

    @Test
    fun `copy fastighet`() {
        val fastighet1 = Fastighet2(
            objektidentitet = Objektidentitet("uuid1"),
            objektversion = 1,
            status = "levande"
        )

        // Nedan funkar inte för Objektidentitet är inte snäll mot ObjectMapper
/*
        val fastighet2 = fastighet1.copy()

        with(fastighet2) {
            assertThat(objektversion).isEqualTo(fastighet1.objektversion)
            assertThat(status).isEqualTo(fastighet1.status)
        }
*/
    }
}