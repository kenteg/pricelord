package ru.pricelord.pricelord.controller

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.pricelord.pricelord.model.Good
import ru.pricelord.pricelord.model.Store
import ru.pricelord.pricelord.model.User
import ru.pricelord.pricelord.model.UserGoods
import ru.pricelord.pricelord.repo.GoodsRepo
import ru.pricelord.pricelord.repo.StoreRepo
import ru.pricelord.pricelord.repo.UserGoodsRepo
import ru.pricelord.pricelord.repo.UsersRepo
import ru.pricelord.pricelord.service.PriceHandler


@RestController
class PriceLordController(
        val goodsRepo: GoodsRepo,
        val userRepo: UsersRepo,
        val userGoodsRepo: UserGoodsRepo,
        val storeRepo: StoreRepo,

        val priceHandler: PriceHandler
) {
    @CrossOrigin(origins=["*", "http://localhost:8080"])
    @GetMapping("/goods")
    fun showAllGoods(): List<UserGoods> {
        return userGoodsRepo.findAll().toList()

    }

    @PostMapping("/goods/add")
    fun fillGoods(@RequestBody good: Good) {
      //  storeRepo.saveAndFlush(good.store)
        goodsRepo.save(good)
    }


    data class ParseReq (val url: String, val query: String)

    @PostMapping("/parse")
    fun parseUrl(@RequestBody req: ParseReq):ResponseEntity<String> {
        val doc: Document = Jsoup.connect(req.url).get()

        return if (doc.select(req.query).hasText()) {
            ResponseEntity.ok("Price: ${doc.select(req.query).text()}")
        } else
            ResponseEntity.ok("Price undefined")
    }


    data class TrackGoodReq (val name: String, val link: String )

    @PostMapping("/trackgood")
    fun trackGood(@RequestBody req: TrackGoodReq):ResponseEntity<String> {
        val good = Good(
                name = req.name,
                link = req.link,
                store = storeRepo.findByNameIgnoreCase(req.link.substring(12, 18))

        )
//        val doc: Document = Jsoup.connect(req.link).get()
//
//        //Только для Mvideo
//        val mvideoPrice = doc.select("body > div.wrapper > div.page-content > div.main-holder > div > div.product-main-information.section > div.o-container__price-column > div > div.fl-pdp-pay.o-pay.u-mb-8 > div.o-pay__content > div.fl-pdp-price > div > div").text()
//
//        val priceValue = mvideoPrice.filter { it.isDigit() }
//        val price = Price(good = good, price = BigDecimal(priceValue))

        val user = User(1, "token", null)
        userRepo.saveAndFlush(user)

        val userGoods = UserGoods(
                user = user,
                goods = mutableListOf(good),
                isNeedNotification = true)

        priceHandler.handle(userGoods)

        userGoodsRepo.saveAndFlush(userGoods)

        return ResponseEntity.ok("success!")
    }
}