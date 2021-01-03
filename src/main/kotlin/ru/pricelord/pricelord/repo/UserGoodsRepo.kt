package ru.pricelord.pricelord.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.pricelord.pricelord.model.Good
import ru.pricelord.pricelord.model.UserGoods

@Repository
interface UserGoodsRepo : JpaRepository<UserGoods, Long>