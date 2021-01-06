package ru.pricelord.pricelord.core.db.service

import mu.KotlinLogging
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
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

        val options = ChromeOptions()

        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors")
        val driver: WebDriver = ChromeDriver(options)

        driver[item.link]
        val caps = DesiredCapabilities()
        caps.isJavascriptEnabled = true

        val priceStr = driver.findElement(By.cssSelector(pathToPrice)).text

        return BigDecimal(priceStr.filter { it.isDigit() })
    }
}