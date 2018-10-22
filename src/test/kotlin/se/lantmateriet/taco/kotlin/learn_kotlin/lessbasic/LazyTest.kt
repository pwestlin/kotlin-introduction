package se.lantmateriet.taco.kotlin.learn_kotlin.lessbasic

import org.junit.Test

class LazyTest {

    class Foo {

        val bar: String by lazy {
            println("Initialized!")
            "baraboom"
        }
    }

    @Test
    fun `test lazy`() {
        val foo = Foo()

        //assertThat(foo.bar).isEqualTo("baraboom")
    }
}

