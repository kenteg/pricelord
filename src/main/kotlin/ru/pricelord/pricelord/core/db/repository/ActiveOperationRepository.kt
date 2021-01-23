package ru.pricelord.pricelord.core.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.core.db.model.ActiveOperation

@Repository
interface ActiveOperationRepository : MongoRepository<ActiveOperation, String> {
    fun findByUserId(userId: String): ActiveOperation?
}