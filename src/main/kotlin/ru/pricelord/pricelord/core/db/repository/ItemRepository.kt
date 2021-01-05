package ru.pricelord.pricelord.core.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.core.db.model.Item

@Repository
interface ItemRepository : JpaRepository<Item, Long> {
    @Query("query finds items with empty price field but with store")
    fun findItemsWithoutPrices(): List<Item>

    @Query("query finds items with empty price field but with store")
    fun findItemsWithoutStore(): List<Item>

    fun findByLink(link: String): Item?
}