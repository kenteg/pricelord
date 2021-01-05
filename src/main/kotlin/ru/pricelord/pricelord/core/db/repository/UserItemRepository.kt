package ru.pricelord.pricelord.core.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.core.db.model.UserItem

@Repository
interface UserItemRepository : JpaRepository<UserItem, Long> {
    fun findByUserId(userId: Long): List<UserItem>
}