package ru.pricelord.pricelord.core.db.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.pricelord.pricelord.core.db.service.PriceService


@Component
class PriceUpdaterScheduler(
        private val priceService: PriceService
) {

    @Scheduled(fixedDelayString = "\${scheduler.priceUpdater.delay}")
    @Transactional(propagation = Propagation.REQUIRED)
    fun parsePrice() {
        priceService.updatePrices()
    }
}