package ru.pricelord.pricelord.service

import org.springframework.stereotype.Service
import ru.pricelord.pricelord.model.Good
import java.math.BigDecimal

@Service

/**
 * Если нужен кастомный парсер для опреденного магазина
 * то создать bean с именем магазина:
 * Например  EldoradoPriceParser
 */
class CustomPriceParser:PriceParser {
    override fun getCurrentPrice(good: Good): BigDecimal {
        TODO("Not yet implemented")
    }

}