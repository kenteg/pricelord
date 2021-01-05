package ru.pricelord.pricelord.service

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.springframework.stereotype.Service
import ru.pricelord.pricelord.model.Good
import java.math.BigDecimal


@Service
class HtmlPriceParser : PriceParser {

    override fun getCurrentPrice(good: Good): BigDecimal {

        val options = ChromeOptions()
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors")
        val driver: WebDriver = ChromeDriver(options)

        driver[good.link]
        val caps = DesiredCapabilities()
        caps.isJavascriptEnabled = true

        val priceStr = driver.findElement(By.cssSelector(good.store.priceSelector)).text

        val priceValue = BigDecimal(priceStr.filter { it.isDigit() })
        return priceValue
    }

}