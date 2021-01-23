package ru.pricelord.pricelord.bot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import ru.pricelord.pricelord.bot.service.PriceLordBot
import javax.annotation.PostConstruct

const val TELEGRAM_BOT = "telegramBot"

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
class BotConfig(val priceLordBot: PriceLordBot) {

    @PostConstruct
    fun loadBots() {
        ApiContextInitializer.init()

        val botsApi = TelegramBotsApi()

        try {
            botsApi.registerBot(priceLordBot)
        } catch (e: TelegramApiException) {
            println(e.printStackTrace())
        }
    }
}

@Component
class BotOptions(
        @Value("\${proxy.host}")
        private val proxyHostVal: String,
        @Value("\${proxy.port}")
        private val proxyPortVal: Int
) : DefaultBotOptions() {
//    init {
//        this.proxyHost = proxyHostVal
//        this.proxyPort = proxyPortVal
//        this.proxyType = ProxyType.SOCKS5
//    }
}