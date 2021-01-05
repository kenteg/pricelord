package ru.pricelord.pricelord.core.db.model

import javax.persistence.*

@Entity
data class UserGoods(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @ManyToOne
        val user: User,
        @ManyToOne
        var goods: Goods,

        var isNeedNotification: Boolean = false
)