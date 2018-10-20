@file:Suppress("PackageName", "unused")

package se.lantmateriet.taco.kotlin.learn_kotlin.basics

// Från och med Kotlin 1.3 kan main deklareras utan parametrar
fun main(args: Array<String>) {
    println("Hello, Wisconsin!")
}

fun thisIsATopLevelFunction(i: Int): Int {
    return i * 2
}

class Foo

// Ja, man kan ha flera klasser per Kotlin-fil
class Bar