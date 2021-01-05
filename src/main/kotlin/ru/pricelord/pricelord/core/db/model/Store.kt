package ru.pricelord.pricelord.core.db.model

import javax.persistence.*

@Entity
data class Store(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val name: String,
        val link: String,
        @Column(name = "path_to_price")
        val pathToPrice: String
)