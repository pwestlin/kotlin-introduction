package se.lantmateriet.taco.kotlin.learn_kotlin.lessbasic

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

typealias CustomerName = String
typealias Address = String

class TypeAliasesTest {


    class Customer(val name: CustomerName, val address: Address)

    @Test
    fun `create customer`() {
        val customer = Customer("Kung Bore", "Kallervalla")

        with(customer) {
            assertThat(name).isEqualTo("Kung Bore")
            assertThat(address).isEqualTo("Kallervalla")
        }

    }
}