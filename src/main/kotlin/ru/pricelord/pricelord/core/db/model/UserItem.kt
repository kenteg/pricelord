package ru.pricelord.pricelord.core.db.model

import javax.persistence.*

@Entity
data class UserItem(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @ManyToOne
        val user: User,
        @ManyToOne
        var item: Item,

        var isNeedNotification: Boolean = false
)