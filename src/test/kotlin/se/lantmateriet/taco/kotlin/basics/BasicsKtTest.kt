package se.lantmateriet.taco.kotlin.basics

import kotlinx.coroutines.experimental.delay

import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.time.LocalDate
import java.time.chrono.ChronoLocalDate
import java.util.*
import kotlin.system.measureTimeMillis

@Suppress("UNUSED_VARIABLE", "UNUSED_VALUE", "RedundantExplicitType", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "VARIABLE_WITH_REDUNDANT_INITIALIZER", "ALWAYS_NULL", "UNNECESSARY_SAFE_CALL", "EXPERIMENTAL_FEATURE_WARNING", "MemberVisibilityCanBePrivate", "SimplifyBooleanWithConstants", "ConstantConditionIf", "MoveLambdaOutsideParentheses", "UnnecessaryVariable", "unused", "UNUSED_PARAMETER", "RemoveRedundantBackticks", "NullChecksToSafeCall")
class BasicsKtTest {

    @Test
    fun `ja en funktion kan namnges sa har`() {
        // ...men bara i tester är detta acceptabelt!
    }

    @Test
    // Funktion med specad returtyp och "body"
    fun functionWithReturnTypeAndBody() {
        // Japp, man kan ha funktioner inuti en funktion - de kallas local functions
        fun double(int: Int): Int {
            return int * 2
        }

        assertThat(double(3)).isEqualTo(6)
    }

    @Test
    fun functionWithExpressionBodyAndAutomaticReturnType() {
        fun square(int: Int) = int * int

        assertThat(square(3)).isEqualTo(9)
    }

    @Test
    fun noReturnTypeForFunction() {
        fun voidFunctionOrWhat() {
            1 + 1
        }

        // Returtypen void saknas, utan kallas Unit och kan utelämnas vid deklaration.
        assertThat(voidFunctionOrWhat()).isInstanceOf(Unit::class.java)
    }

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

    @Test
    fun stringTemplates() {
        val name = "Peter"
        val age = 46

        assertThat("$name är $age år gam...ung").isEqualTo("Peter är 46 år gam...ung")
    }


    @Test
    fun conditionalExpressions() {
        // Conditional statements returnerar värden!
        val strFromCond = if (2 > 1) 2 else 1

        assertThat(strFromCond).isEqualTo(2)
    }

    @Test
    fun nulls() {
        var string: String = "a"
        //string = null     // Kompileringsfel för var string får inte vara null

        // Notera ? efter String
        var nullableString: String? = "b"
        nullableString = null

        assertThatThrownBy { nullableString!!.length }
            .isInstanceOf(KotlinNullPointerException::class.java)
            .hasMessage(null)
    }

    @Test
    fun nullableCheck() {
        class Address(val street: String? = null)
        class Person(val address: Address? = null)
        class Customer(val id: Long, val person: Person?)

        fun getCustomerStreetAddress(customer: Customer?): String? {
            // TODO petves: Gör om till safe-calls
            if (customer != null && customer.person != null && customer.person.address != null && customer.person.address.street != null) {
                return customer.person.address.street
            } else {
                return null
            }

        }

        assertThat(getCustomerStreetAddress(null)).isNull()
        assertThat(getCustomerStreetAddress(Customer(1, Person()))).isNull()
        assertThat(getCustomerStreetAddress(Customer(2, Person(Address())))).isNull()
        assertThat(getCustomerStreetAddress(Customer(3, Person(Address("Lantmäterigatan"))))).isEqualTo("Lantmäterigatan")

        //return customer?.person?.address?.street

        val files = File("/asfgsdhd/cvhndf/jmfghjern").listFiles()    // listFiles() returnerar null om inte pathen kan hittas
        assertThat(files).isNull()

        assertThatThrownBy { files.size }
            .isInstanceOf(NullPointerException::class.java)
            .hasMessage(null)

        assertThat(files?.size).isNull()
        println(files?.size)    // Java: if(files != null) {System.out.println(files.size)}

        println(files?.size ?: "empty")     // Java: *puh*

        var value: String? = null
        value?.let {
            println("Inte null")    // Denna rad kommer aldrig exekveras då value är null
        }
        value = "foo"
        value?.let {
            println("Inte null")
        }
    }

    @Test
    fun `OrElse, OrElseThrow`() {
        fun canReturnNull(string: String?) = string

        // ?: i kotlin är samma som Javas .orElse(...)
        assertThat(canReturnNull("foo") ?: "null").isEqualTo("foo")
        assertThat(canReturnNull(null) ?: "null").isEqualTo("null")

        // ...och faktiskt även samma som Javas orElseThrow
        assertThatThrownBy { canReturnNull(null) ?: throw RuntimeException("Jag ville inte ha null!") }
            .isInstanceOf(RuntimeException::class.java)
            .hasMessage("Jag ville inte ha null!")

    }

    @Test
    // Jämför med Javas switch
    fun whenExpressions() {
        val obj: Any = 0.23

        val string =
            when (obj) {
                1 -> "One"
                "Hello" -> "Greeting"
                is Long -> "Long"
                !is String -> "Not a string"
                else -> "Unknown"
            }

        assertThat(string).isEqualTo("Not a string")
    }

    @Test
    fun ranges() {
        val x = 5
        val y = 7
        if (x in 1..y) {
            println("in range")
        }

        // progressions
        for (num in 1..10 step 2) {
            print("$num ")
        }
        println()
        for (num in 9 downTo 0 step 3) {
            print("$num ")
        }
    }

    @Test
    fun collections() {
        val fruits = listOf("banana", "avocado", "apple", "kiwifruit", "orange")

        when {
            "orange" in fruits -> println("juicy")
            "apple" in fruits -> println("apple is fine too")
            else -> println("whatever")
        }


        val fruitsWithA = fruits            // Inget anrop till stream()
            //.filter { s -> s.startsWith("a") }
            .filter { it.startsWith("a") }      // "it" är default-namnet för en ensam parameter i strömmar
            .sortedBy { it }
            .map { it.toUpperCase() }
        // Inget anrop till .collect(Collectors.toList())
        assertThat(fruitsWithA).containsExactlyInAnyOrder("AVOCADO", "APPLE")
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

    @Test
    fun tryCatch() {
        // try-satser kan returnera ett värde
        val value = try {
            Integer.parseInt("7")
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        println("value = $value")
    }

    @Test
    fun checkedExceptions() {
        // Kotlin does not have checked exceptions - YAAAAAAAAAAAAAAAAY!
        // ...men hur sjutton blir det när man anropar en Java-metod som kastar ett checked exception?

        // readBytes() kastar ett IOException (checked exception) om en fil inte kan tas bort
        //println(File("/d2362/sdhdhs/xbryud/sdfghsdg.txt").readBytes())

        // ...men om jag vill ta hand om felet då?
        try {
            println(File("/d2362/sdhdhs/xbryud/sdfghsdg.txt").readBytes())
        } catch (e: IOException) {
            println("Do something")
            e.printStackTrace()
        }
    }

    @Test
    fun `TODO`() {
        assertThatThrownBy { TODO("Implementera mig då!") }
            .isInstanceOf(NotImplementedError::class.java)
            .hasMessage("An operation is not implemented: Implementera mig då!")
    }

    @Test
    fun `try with resources`() {
        /*
            // Java 1.7 and above
            Properties prop = new Properties();
            try (FileInputStream fis = new FileInputStream("config.properties")) {
                prop.load(fis);
            }
            // fis automatically closed
         */

        val prop = Properties()
        try {
            FileInputStream("config.properties").use {
                prop.load(it)
            }
            // FileInputStream automatically closed
        } catch (e: Throwable) {
            // Do nothing
        }
    }

    @Test
    fun `calling multiple methods on an object instance`() {
        class Turtle {
            fun penDown() = println("Down")
            fun penUp() = println("Up")
            fun turn(degrees: Double) = println("turning $degrees")
            fun forward(pixels: Double) = println("forward $pixels")
        }

        val myTurtle = Turtle()
        // with "väljer" en objektinstans jag vill jobba med i kontextet
        with(myTurtle) {
            //draw a 100 pix square
            penDown()
            for (i in 1..4) {
                forward(100.0)
                turn(90.0)
            }
            penUp()
        }
    }

    @Test
    fun `classes and objects`() {
        // Japp, man kan skapa klasser inuti en funktion
        class Car(val brand: String, var name: String)

        val car = Car("Volvo", "Pärlan")
        assertThat(car.brand).isEqualTo("Volvo")
        assertThat(car.name).isEqualTo("Pärlan")

        //car.brand = "Opel"    // Näru, detta går inte!
        car.name = "Skrothögen"
        assertThat(car.brand).isEqualTo("Volvo")
        assertThat(car.name).isEqualTo("Skrothögen")

        println("car = ${car}")
    }

    @Test
    fun inheritance() {
        // Klasser är final by default som man måste märka dem med "open" vid arv
        open class Animal(val noLegs: Int) {
            // Metoder är också final by default
            open fun sound() = "Schhh"
        }

        class Fish : Animal(0) {
            override fun sound() = "Blubb"
        }

        class Fox() : Animal(4) {
            override fun sound() = "What does twe fox say?"
        }

        val threeLeggedAnimal = Animal(3)
        assertThat(threeLeggedAnimal.noLegs).isEqualTo(3)
        assertThat(threeLeggedAnimal.sound()).isEqualTo("Schhh")

        val fox = Fox()
        assertThat(fox.noLegs).isEqualTo(4)
        assertThat(fox.sound()).isEqualTo("What does twe fox say?")


        //class Salmon : Fish       // Hä gånt nå för Fish är final (by default)
    }

    @Test
    fun `classes and objects - dataClasses`() {
        data class Person(val name: String, val age: Int)

        val person = Person("Peter", 46)
        println("person = ${person}")
        println("${person.name} är ${person.age} år gam...ung")

        assertThat(person.name).isEqualTo("Peter")
        assertThat(person.age).isEqualTo(46)
    }



    class Stack<E>(vararg items: E) {
        private val elements = items.toMutableList()

        fun push(item: E) {elements.add(item)}
        fun pop() = elements.removeAt(elements.size - 1)
        fun peek(): E = elements.last()
    }

    @Test
    fun generics() {
        val stack = Stack("foo", 4)
        assertThat(stack.peek()).isEqualTo(4)
        stack.push(5.67)
        assertThat(stack.peek()).isEqualTo(5.67)
        assertThat(stack.pop()).isEqualTo(5.67)
        assertThat(stack.pop()).isEqualTo(4)
        assertThat(stack.peek()).isEqualTo("foo")

        val intStack = Stack(1, 6, 3)
        assertThat(intStack.peek()).isEqualTo(3)
        //intStack.push("foo")      // Går inte eftersom stacken automatiskt typats till int
    }

    @Test
    fun `generic functions`() {
        fun <E> mutableStackOf(vararg elements: E) = Stack(*elements)

        val genericStack = mutableStackOf("a", 1)
        genericStack.push(12.32)

        val intStack = mutableStackOf(1, 2 ,3)    // Går inte för "a" och 2.34 är inte Int
        // intStack.push("foo")      // Går inte för typen har automatiskt blivit Int
        // val intStack = mutableStackOf<Int>("a", 1, 2.34)    // Går inte för "a" och 2.34 är inte Int
    }


    object Resource {
        const val name = "I am a singleton"
        const val value = 42
    }

    @Test
    fun singleton() {
        assertThat(Resource.name).isEqualTo("I am a singleton")
        assertThat(Resource.value).isEqualTo(42)
    }

    @Test
    fun `infix member function`() {
        class Person(name: String) {
            val likedStruff = mutableListOf<String>()
            infix fun likes(thing: String) {
                likedStruff.add(thing)
            }
        }

        val peter = Person("Peter")
        peter likes "Kotlin"
        peter likes "Karting"

        assertThat(peter.likedStruff).containsExactlyInAnyOrder("Karting", "Kotlin")
    }


    /*
    In Kotlin, unlike Java or C#, classes do not have static methods. In most cases, it's recommended to simply use package-level functions instead.
    If you need to write a function that can be called without having a class instance but needs access to the internals of a class (for example, a factory method),
    you can write it as a member of an object declaration inside that class.
    Even more specifically, if you declare a companion object inside your class, you'll be able .to
    call its members with the same syntax as calling static methods in Java/C#, using only the class name as a qualifier.
     */

    class Person private constructor(val name: String) {
        companion object Factory {
            fun create(firstname: String, surname: String): Person = Person("$firstname $surname")
        }
    }

    @Test
    fun `companion objects`() {
        val myClass = Person.create("Pelle", "Plutt")

        assertThat(myClass.name).isEqualTo("Pelle Plutt")
    }

    @Test
    fun let() {
        class Foo(arg: String)

        Foo("Bar").let { foo ->
            foo.toString()
            foo.equals(Foo("Bra"))
        }
    }

    @Test
    fun apply() {
        val dir = System.getProperty("java.io.tmpdir") + "/bulle"
        val createdDir = File(dir).apply { mkdirs() }
        assertThat(File(System.getProperty("java.io.tmpdir") + "/bulle")).exists()

        val i = 7.apply { minus(3) }
        assertThat(i).isEqualTo(7)      // Varför 7 och inte 4?
    }

    @Test
    fun `extension functions`() {
        // Här börjar det bli fränt på riktigt!

        val string = "foo"
        assertThat(string.reversed().toUpperCase()).isEqualTo("OOF")
        // Vi vill att det ska finnas en funktion för att reversera strängen och göra den till versaler

        fun String.reverseAndToUpperCase() = this.reversed().toUpperCase()
        assertThat(string.reverseAndToUpperCase()).isEqualTo("OOF")

        // Ni vet den där klassen StringUtils eller DateUtils som man ALLTID har i VARJE projekt?
        // ...den behövs inte längre för man kan använda extension functions istället! :)
    }

    @Test
    fun `infix extension function`() {
        infix fun Int.add(i: Int) = this + i

        assertThat(7.add(3)).isEqualTo(10)
        assertThat(7 add 3).isEqualTo(10)
    }

    @Test
    fun `operator functions`() {
        operator fun Int.times(str: String) = str.repeat(this)
        assertThat(2 * "Bye ").isEqualTo("Bye Bye ")

        operator fun String.get(range: IntRange) = substring(range)  // 3
        val str = "Always forgive your enemies; nothing annoys them so much."
        assertThat(str[0..13]).isEqualTo("Always forgive")
    }

    @Test
    fun varargs() {
        fun concat(vararg strings: String): String {
            return strings.joinToString(" ")
        }

        assertThat(concat("Varargs", "can", "be", "useful")).isEqualTo("Varargs can be useful")
    }

    enum class Motorcycle(val brand: String) {
        SUZUKI("Suzuki"),
        HONDA("Honda")
    }

    @Test
    fun enums() {
        assertThat(Motorcycle.valueOf("HONDA")).isEqualTo(Motorcycle.HONDA)
        assertThat(Motorcycle.SUZUKI.brand).isEqualTo("Suzuki")
    }

    @Test
    fun `lambda compute`() {
        val add = { a: Int, b: Int -> a + b }
        val subtract = { a: Int, b: Int -> a - b }
        val multiply = { a: Int, b: Int -> a * b }

        fun compute(a: Int, b: Int, function: (Int, Int) -> Int): Int {
            return function(a, b)
        }

        assertThat(compute(4, 2, add)).isEqualTo(6)
        assertThat(compute(4, 2, subtract)).isEqualTo(2)
        assertThat(compute(4, 2, multiply)).isEqualTo(8)
        assertThat(compute(4, 2, { a: Int, b: Int -> a / b })).isEqualTo(2)
        assertThat(compute(4, 2) { a: Int, b: Int -> a / b }).isEqualTo(2)
    }

    @Test
    fun `collections - mutable and immutable`() {
        val numbers = listOf(1, 2, 3)
        assertThat(numbers).containsExactly(1, 2, 3)
        //numbers.add(4)    // add finns inte som funktion på en immutable list

        val numbers2 = mutableListOf(1, 2, 3)
        assertThat(numbers2).containsExactly(1, 2, 3)
        numbers2.add(4)
        assertThat(numbers2).containsExactly(1, 2, 3, 4)
    }

    @Test
    fun `type checks`() {
        fun printLength(obj: Any) {
            if (obj is String) {
                println(obj.length)
            }

            if (obj !is String) { // same as !(obj is String)
                println("Not a String")
            } else {
                println(obj.length)
            }
        }

        printLength("foo")
        printLength(123)
    }

    @Test
    fun `smart casts`() {
        //val date: ChronoLocalDate? = LocalDate.now()
        val date: ChronoLocalDate? = null
        if (date != null) {
            println(date.isLeapYear)
        }

        if (date != null && date.isLeapYear) {
            println("It's a leap year!")
        }

        if (date == null || !date.isLeapYear) {
            println("There's no Feb 29 this year...")
        }

        if (date is LocalDate) {
            val month = date.monthValue
            println(month)
        }

        /////////////////////////

        fun foo(obj: Any) {
            if (obj is String) {
                println("obj.length = ${obj.length}")
            }
        }

        foo("sgsx")
        foo(72.3)
    }

    data class Car(val brand: String, val model: String, val year: String)

    @Test
    fun equality() {
        val car = Car("Volvo", "PV", "1970")

        val equalCar = Car("Volvo", "PV", "1970")
        assertThat(equalCar == car).isTrue()

        val notEqualCar = Car("Volvo", "Amazon", "1966")
        assertThat(notEqualCar == car).isFalse()

        assertThat(notEqualCar === car).isFalse()

        val sameCar = car
        assertThat(sameCar == car).isTrue()
        assertThat(sameCar === car).isTrue()
    }


    data class User(val username: String, val email: String)

    @Test
    fun `destructuring declarations`() {
        val user = User("bosseringholmo", "bosse_man@oldfolkshome.se")

        val (username, email) = user
        assertThat(username).isEqualTo("bosseringholmo")
        assertThat(email).startsWith("bosse_man")
    }

    @Test
    fun coroutines() {
        val time = measureTimeMillis {
            runBlocking {
                repeat(100_000) {
                    // launch a lot of coroutines
                    launch {
                        delay(1000L)
                        print(".")
                    }
                }
            }
        }
        println("\ntime = $time ms")
    }

}