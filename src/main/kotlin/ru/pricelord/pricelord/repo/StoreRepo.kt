package ru.pricelord.pricelord.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.model.Store

@Repository
interface StoreRepo : JpaRepository<Store, Long> {
    fun findByNameIgnoreCase(name: String): Store
}