package ru.pricelord.pricelord.input.web.controller

import org.springframework.web.bind.annotation.*
import ru.pricelord.pricelord.core.db.model.UserGoods
import ru.pricelord.pricelord.core.db.service.UserGoodsService
import ru.pricelord.pricelord.input.web.model.request.UserGoodsRequest

@RestController("/v1")
class UserGoodsController(
        val userGoodsService: UserGoodsService
) {

    @CrossOrigin(origins = ["*", "http://localhost:8080"])
    @GetMapping("/goods")
    fun showAllGoods(@RequestParam userId: Long): List<UserGoods> {
        return userGoodsService.findGoodsByUserId(userId)
    }

    @PostMapping("/goods")
    fun addGoods(@RequestBody userGoodsRequest: UserGoodsRequest): UserGoods {
        return userGoodsService.saveUserGoods(userGoodsRequest)
    }
}