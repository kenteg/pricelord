package ru.pricelord.pricelord.core.db.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user_item")
data class UserItem(
        @field:Id
        val id: String? = null,
        @field:Indexed(unique = true)
        val userId: String,
        val name: String,
        @field:DBRef
        var item: Item,
        var isNeedNotification: Boolean = false
)