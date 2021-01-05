package ru.pricelord.pricelord.core.db.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.pricelord.pricelord.core.db.service.PriceService


@Component
class PriceUpdaterScheduler(
        private val priceService: PriceService
) {

    @Scheduled(fixedDelayString = "\${scheduler.priceUpdater.delay}")
    fun parsePrice() {
        priceService.updatePrices()
    }
}