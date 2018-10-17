package se.lantmateriet.taco.kotlin

import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*

@SpringBootApplication
class KotlinApplication

fun main(args: Array<String>) {
    val application = runApplication<KotlinApplication>(*args)
    val service = application.getBean<GreetingService>()
    println(service.greet("Sune"))
}

@Repository
class GreetingRepository {
    val repo = listOf("Hi", "Yo", "Hello")

    fun greeting() : String {
        return repo[Random().nextInt(repo.size)]
    }
}

@Service
class GreetingService(val repo: GreetingRepository) {

    fun greet(name: String) =
        "${repo.greeting()}, $name!"
}