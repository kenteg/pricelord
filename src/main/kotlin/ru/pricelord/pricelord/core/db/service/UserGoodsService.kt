package ru.pricelord.pricelord.core.db.service

import org.springframework.stereotype.Service
import ru.pricelord.pricelord.core.db.errors.UserNotFoundException
import ru.pricelord.pricelord.core.db.model.Goods
import ru.pricelord.pricelord.core.db.model.UserGoods
import ru.pricelord.pricelord.core.db.repository.UserGoodsRepository
import ru.pricelord.pricelord.core.db.repository.UserRepository
import ru.pricelord.pricelord.input.web.model.request.UserGoodsRequest

@Service
class UserGoodsService(
        private val userGoodsRepository: UserGoodsRepository,
        private val userRepository: UserRepository,
        private val goodsService: GoodsService,
        private val linkService: LinkService
) {
    fun findGoodsByUserId(userId: Long): List<UserGoods> =
            userGoodsRepository.findByUserId(userId)

    fun saveUserGoods(userGoodsRequest: UserGoodsRequest): UserGoods {
        val formattedLink = linkService.formatLink(userGoodsRequest.link)
        val goods = goodsService.findGoodsByLink(formattedLink) ?: goodsService.saveGoods(Goods(link = formattedLink))

        val userGoods = UserGoods(
                user = userRepository.findById(userGoodsRequest.userId).orElseThrow {
                    UserNotFoundException("User with id ${userGoodsRequest.userId} not found")
                },
                isNeedNotification = userGoodsRequest.isNeedNotification,
                goods = goods
        )

        return userGoodsRepository.saveAndFlush(userGoods)
    }
}