package ru.pricelord.pricelord.core.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.core.db.model.Goods
import ru.pricelord.pricelord.core.db.model.UserGoods

@Repository
interface UserGoodsRepository : JpaRepository<UserGoods, Long> {
    fun findByUserId(userId: Long): List<UserGoods>
}