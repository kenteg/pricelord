package ru.pricelord.pricelord.core.db.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.core.db.model.Price

@Repository
interface PriceRepository : MongoRepository<Price, String>