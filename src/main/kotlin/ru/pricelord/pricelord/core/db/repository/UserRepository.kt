package ru.pricelord.pricelord.core.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.core.db.model.User

@Repository
interface UserRepository : MongoRepository<User, String>