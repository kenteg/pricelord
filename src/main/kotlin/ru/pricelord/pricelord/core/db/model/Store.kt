package ru.pricelord.pricelord.core.db.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "store")
data class Store(
        @field:Id
        val id: String? = null,
        val name: String,
        @field:Indexed
        val link: String,
        val pathToPrice: String
)