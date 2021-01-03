package ru.pricelord.pricelord

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PricelordApplication

fun main(args: Array<String>) {
	runApplication<PricelordApplication>(*args)
}
