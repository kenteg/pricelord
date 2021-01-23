package ru.pricelord.pricelord.core.db.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "user")
data class User(
        @field:Id
        val id: String? = null,
        val name: String? = null,
        val email: String? = null,
        val googleToken: String? = null,
        val vkToken: String? = null,
        val registrationDate: LocalDateTime = LocalDateTime.now(),
        val imageUrl: String? = null
)