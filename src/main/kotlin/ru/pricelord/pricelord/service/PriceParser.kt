package ru.pricelord.pricelord.service

import ru.pricelord.pricelord.model.Good
import java.math.BigDecimal

interface PriceParser {

    fun getCurrentPrice(good: Good): BigDecimal
}