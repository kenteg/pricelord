package ru.pricelord.pricelord

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PricelordApplication

fun main(args: Array<String>) {
	System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver") // TODO Extract to application properties
	System.setProperty("webdriver.chrome.logfile", "chromedriver.log");
	runApplication<PricelordApplication>(*args)
}
