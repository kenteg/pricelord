package ru.pricelord.pricelord.core.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.core.db.model.Store

@Repository
interface StoreRepository : JpaRepository<Store, Long>