package ru.pricelord.pricelord.core.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.pricelord.pricelord.core.service.PriceService


@Component
class PriceParserScheduler(
    private val priceService: PriceService
) {

    @Scheduled(fixedDelayString = "\${scheduler.priceParser.delay}")
    @Transactional(propagation = Propagation.REQUIRED)
    fun parsePrice() {
        priceService.updatePrices()
    }
}