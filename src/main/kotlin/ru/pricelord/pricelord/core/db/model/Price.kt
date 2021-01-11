package ru.pricelord.pricelord.core.db.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "price")
data class Price(
        @field:Id
        val id: String? = null,
        @field:Indexed
        val itemId: String,
        val price: BigDecimal
)