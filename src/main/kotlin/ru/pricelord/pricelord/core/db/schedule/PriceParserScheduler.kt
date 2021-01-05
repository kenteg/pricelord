package ru.pricelord.pricelord.core.db.schedule

import org.springframework.scheduling.annotation.Scheduled



class PriceParserScheduler {

    @Scheduled(fixedDelay = "\${scheduler.priceParser}")
    fun parsePrice() {
        println(
                "Fixed delay task - " + System.currentTimeMillis() / 1000)
    }
}