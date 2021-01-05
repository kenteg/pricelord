package ru.pricelord.pricelord.core.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.core.db.model.Goods

@Repository
interface GoodsRepository : JpaRepository<Goods, Long> {
    @Query("query finds goods with empty price field but with store")
    fun findGoodsWithoutPrices(): List<Goods>

    @Query("query finds goods with empty price field but with store")
    fun findGoodsWithoutStore(): List<Goods>

    fun findByLink(link: String): Goods?
}