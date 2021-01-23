package ru.pricelord.pricelord.core.db.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "item")
data class Item(
        @field:Id
        val id: String? = null,
        @field:Indexed(unique = true)
        val link: String,
        @field:Indexed
        @field:DBRef
        var store: Store? = null,
        @field:DBRef
        var lastPrice: Price? = null
)