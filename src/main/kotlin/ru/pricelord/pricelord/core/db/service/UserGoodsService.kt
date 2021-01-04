package ru.pricelord.pricelord.core.db.service

import org.springframework.stereotype.Service
import ru.pricelord.pricelord.core.db.errors.UserNotFoundException
import ru.pricelord.pricelord.core.db.model.Goods
import ru.pricelord.pricelord.core.db.model.UserGoods
import ru.pricelord.pricelord.core.db.repository.UserGoodsRepository
import ru.pricelord.pricelord.core.db.repository.UserRepository

@Service
class UserGoodsService(
        private val userGoodsRepository: UserGoodsRepository,
        private val userRepository: UserRepository
) {
    fun findGoodsByUserId(userId: Long): List<UserGoods> =
            userGoodsRepository.findByUserId(userId)

    fun saveGoods(userId: Long, isNeedNotification: Boolean, goods: Goods) {
        val userGoods = UserGoods(
                user = userRepository.findById(userId).orElseThrow {
                    UserNotFoundException("User with id $userId not found")
                },
                isNeedNotification = isNeedNotification,
                goods = goods
        )
        userGoodsRepository.saveAndFlush(userGoods)
    }
}