package ru.pricelord.pricelord.core.db.model

import javax.persistence.*

@Entity
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val authToken: String?,
        @OneToOne
        val userItem: UserItem?
)