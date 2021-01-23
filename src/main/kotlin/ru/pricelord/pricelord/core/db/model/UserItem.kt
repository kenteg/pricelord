package ru.pricelord.pricelord.core.db.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "user_item")
data class UserItem(
        @field:Id
        val id: String? = null,
        @field:Indexed(unique = true)
        val userId: String,
        @field:CreatedDate
        val createDate: LocalDateTime? = null,
        val name: String,
        @field:Indexed
        var itemId: String,
        var isNeedNotification: Boolean = false
)