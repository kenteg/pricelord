package ru.pricelord.pricelord

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PriceLordApplication

fun main(args: Array<String>) {
	runApplication<PriceLordApplication>(*args)
}
