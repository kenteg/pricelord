package ru.pricelord.pricelord.core.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.core.db.model.User
import java.util.*

@Repository
interface UserRepository : MongoRepository<User, String>{
    fun findByEmail(email: String): Optional<User>
}
