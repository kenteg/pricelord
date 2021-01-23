package ru.pricelord.pricelord.core.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.core.db.model.Item

@Repository
interface ItemRepository : MongoRepository<Item, String> {
    @Query("{ 'lastPrice' : null}")
    fun findItemsWithoutPrices(): List<Item>

    @Query("{ 'store' : null }")
    fun findItemsWithoutStore(): List<Item>

    fun findByLink(link: String): Item?
}