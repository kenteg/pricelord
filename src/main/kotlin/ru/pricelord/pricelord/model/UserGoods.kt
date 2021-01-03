package ru.pricelord.pricelord.model

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
data class UserGoods(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @OneToOne
        val user: User,
        @OneToMany(cascade = [CascadeType.ALL])
        var goods: MutableList<Good>,
        @OneToOne(cascade = [CascadeType.ALL])
        var price: Price?,

        var isNeedNotification: Boolean = false,
)