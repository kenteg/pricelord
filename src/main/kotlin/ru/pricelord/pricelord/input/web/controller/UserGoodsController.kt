package ru.pricelord.pricelord.input.web.controller

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.pricelord.pricelord.core.db.model.Goods
import ru.pricelord.pricelord.core.db.model.Price
import ru.pricelord.pricelord.core.db.model.Store
import ru.pricelord.pricelord.core.db.model.User
import ru.pricelord.pricelord.core.db.model.UserGoods
import ru.pricelord.pricelord.core.db.repository.GoodsRepository
import ru.pricelord.pricelord.core.db.repository.StoreRepository
import ru.pricelord.pricelord.core.db.repository.UserGoodsRepository
import ru.pricelord.pricelord.core.db.repository.UserRepository
import ru.pricelord.pricelord.core.db.service.UserGoodsService
import ru.pricelord.pricelord.input.web.model.request.GoodsRequest
import ru.pricelord.pricelord.input.web.model.request.toGoods
import java.math.BigDecimal

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
    fun addGoods(@RequestBody goodsRequest: GoodsRequest) {
        userGoodsService.saveGoods(
                userId = goodsRequest.userId,
                isNeedNotification = goodsRequest.isNeedNotification,
                goods = goodsRequest.toGoods()
        )
    }


    data class ParseReq(val url: String, val query: String)

    @PostMapping("/parse")
    fun parseUrl(@RequestBody req: ParseReq): ResponseEntity<String> {
        val doc: Document = Jsoup.connect(req.url).get()

        return if (doc.select(req.query).hasText()) {
            ResponseEntity.ok("Price: ${doc.select(req.query).text()}")
        } else
            ResponseEntity.ok("Price undefined")
    }


    data class TrackGoodReq(val name: String, val link: String)

    @PostMapping("/trackgood")
    fun trackGood(@RequestBody req: TrackGoodReq): ResponseEntity<String> {
        val good = Goods(
                name = req.name,
                link = req.link,
                store = Store(
                        name = req.link.substring(12, 18),
                        link = req.link.substring(0, 22)
                )
        )
        val doc: Document = Jsoup.connect(req.link).get()

        //Только для Mvideo
        val mvideoPrice = doc.select("body > div.wrapper > div.page-content > div.main-holder > div > div.product-main-information.section > div.o-container__price-column > div > div.fl-pdp-pay.o-pay.u-mb-8 > div.o-pay__content > div.fl-pdp-price > div > div").text()

        val priceValue = mvideoPrice.filter { it.isDigit() }
        val price = Price(goods = good, price = BigDecimal(priceValue))

        val user = User(1, "token", null)
        userRepository.saveAndFlush(user)

        val userGoods = UserGoods(
                user = user,
                goods = mutableListOf(good),
                price = price,
                isNeedNotification = true)

        userGoodsRepository.saveAndFlush(userGoods)

        return ResponseEntity.ok("success!")
    }
}