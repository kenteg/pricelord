package ru.pricelord.pricelord.core.service

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
import ru.pricelord.pricelord.core.db.repository.StoreRepository
import java.math.BigDecimal

@Service
class PriceService(
    private val itemRepository: ItemRepository,
    private val priceRepository: PriceRepository,
    private val storeRepository: StoreRepository
) {

    private val log = KotlinLogging.logger {}

    fun updatePrices() {
        val items = itemRepository.findItemsWithoutPrices()
        items.forEach {
            try {
                if (it.store == null) //store scheduler hasn't started yet
                    return

                val priceValue = parsePrice(it)
                val price = Price(price = priceValue)

                val lastPrice = priceRepository.save(price)
                itemRepository.save(it.apply { this.lastPrice = lastPrice })
            } catch (ex: Throwable) {
                log.error("Error while parse price for item: ${it.id} - ${it.link}", ex)
            }
        }
    }

    fun parsePrice(item: Item): BigDecimal {
        val store = storeRepository.findById(item.store?.id!!)
        val pathToPrice = store.get().pathToPrice

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