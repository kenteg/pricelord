package ru.pricelord.pricelord.core.db.model

import javax.persistence.*

@Entity
data class Goods(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val link: String,
        @OneToMany(cascade = [CascadeType.ALL])
        var price: List<Price> = emptyList(),
        @OneToOne(cascade = [CascadeType.ALL])
        var store: Store? = null
)