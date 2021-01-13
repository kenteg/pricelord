package ru.pricelord.pricelord.core.schedule

import com.nhaarman.mockito_kotlin.*
import org.junit.jupiter.api.Test
import ru.pricelord.pricelord.core.service.PriceService

internal class PriceParserSchedulerTest {

    private val priceService = mock<PriceService>()
    private val scheduler = PriceParserScheduler(priceService)

    @Test
    fun parsePrice() {
        doNothing().whenever(priceService).updatePrices()

        scheduler.parsePrice()

        verify(priceService, times(1)).updatePrices()
    }
}