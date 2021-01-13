package ru.pricelord.pricelord.core.service

import com.nhaarman.mockito_kotlin.*
import org.junit.jupiter.api.Test
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.core.db.model.Price
import ru.pricelord.pricelord.core.db.repository.ItemRepository
import ru.pricelord.pricelord.core.db.repository.PriceRepository
import ru.pricelord.pricelord.core.db.repository.StoreRepository
import ru.pricelord.pricelord.getObject
import java.math.BigDecimal

internal class PriceServiceTest {
    private val itemRepository = mock<ItemRepository>()
    private val priceRepository = mock<PriceRepository>()
    private val storeRepository = mock<StoreRepository>()
    private val priceService = PriceService(itemRepository, priceRepository, storeRepository)

    @Test
    fun updatePrices() {
        val item1 = getObject<Item>("item/item_mvideo_8888.json").copy(id = "1", lastPriceId = null)
        val item2 = getObject<Item>("item/item_mvideo_9999.json").copy(id = "2", lastPriceId = null)
        whenever(itemRepository.findItemsWithoutPrices()).thenReturn(listOf(item1, item2))
        val price1 = Price(null, item1.id!!, BigDecimal.valueOf(10))
        val price2 = Price(null, item2.id!!, BigDecimal.valueOf(20))

        val spiedPriceService = spy(priceService)

        doReturn(price1.price).whenever(spiedPriceService).parsePrice(item1)
        doReturn(price2.price).whenever(spiedPriceService).parsePrice(item2)
        whenever(priceRepository.save(price1)).thenReturn(price1.copy(id = "1"))
        whenever(priceRepository.save(price2)).thenReturn(price2.copy(id = "2"))
        whenever(itemRepository.save(item1.copy(lastPriceId = "1"))).thenReturn(item1)
        whenever(itemRepository.save(item2.copy(lastPriceId = "2"))).thenReturn(item2)

        spiedPriceService.updatePrices()

        verify(spiedPriceService, times(1)).parsePrice(item1)
        verify(spiedPriceService, times(1)).parsePrice(item2)
        verify(priceRepository, times(1)).save(price1)
        verify(priceRepository, times(1)).save(price2)
        verify(itemRepository, times(1)).save(item1.copy(lastPriceId = "1"))
        verify(itemRepository, times(1)).save(item2.copy(lastPriceId = "2"))
    }
}