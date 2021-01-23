package ru.pricelord.pricelord

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class PriceLordApplication

fun main(args: Array<String>) {
	runApplication<PriceLordApplication>(*args)

}
