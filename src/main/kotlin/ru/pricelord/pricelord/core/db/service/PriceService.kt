package ru.pricelord.pricelord.core.db.service

import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.core.db.model.Price
import ru.pricelord.pricelord.core.db.repository.ItemRepository
import ru.pricelord.pricelord.core.db.repository.PriceRepository
import java.math.BigDecimal

@Service
class PriceService(
        private val itemRepository: ItemRepository,
        private val priceRepository: PriceRepository
) {

    private val log = KotlinLogging.logger {}

    fun updatePrices() {
        val items = itemRepository.findItemsWithoutPrices()
        items.forEach {
            try {
                val priceValue = parsePrice(it)
                val price = Price(item = it, price = priceValue)

                priceRepository.saveAndFlush(price)
            } catch (ex: Throwable) {
                log.error("Error while parse price for item: ${it.id} - ${it.link}", ex)
            }
        }
    }

    fun parsePrice(item: Item): BigDecimal {
        val pathToPrice = item.store!!.pathToPrice

        val doc: Document = Jsoup.connect(item.link).get()

        val priceText = doc.select(pathToPrice).text()

        return BigDecimal(priceText.filter { it.isDigit() })
    }
}