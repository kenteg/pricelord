package ru.pricelord.pricelord.core.db.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
data class Price(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @ManyToOne
        val goods: Goods,
        val price: BigDecimal
)