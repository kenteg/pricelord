package ru.pricelord.pricelord.core.db.service

import mu.KotlinLogging
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service
import ru.pricelord.pricelord.core.db.model.Goods
import ru.pricelord.pricelord.core.db.model.Price
import ru.pricelord.pricelord.core.db.repository.GoodsRepository
import ru.pricelord.pricelord.core.db.repository.PriceRepository
import java.math.BigDecimal

@Service
class PriceService(
        private val goodsRepository: GoodsRepository,
        private val priceRepository: PriceRepository
) {

    private val log = KotlinLogging.logger {}

    fun updatePrices() {
        val goods = goodsRepository.findGoodsWithoutPrices()
        goods.forEach {
            try {
                val priceValue = parsePrice(it)
                val price = Price(goods = it, price = priceValue)

                priceRepository.saveAndFlush(price)
            } catch (ex: Throwable) {
                log.error("Error while parse price for goods: ${it.id} - ${it.link}", ex)
            }
        }
    }

    fun parsePrice(goods: Goods): BigDecimal {
        val pathToPrice = goods.store!!.pathToPrice

        val doc: Document = Jsoup.connect(goods.link).get()

        val priceText = doc.select(pathToPrice).text()

        return BigDecimal(priceText.filter { it.isDigit() })
    }
}