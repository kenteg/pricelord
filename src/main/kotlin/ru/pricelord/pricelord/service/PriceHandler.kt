package ru.pricelord.pricelord.service

import org.springframework.stereotype.Service
import ru.pricelord.pricelord.model.Price
import ru.pricelord.pricelord.model.UserGoods
import ru.pricelord.pricelord.repo.UserGoodsRepo
import java.util.*

@Service
class PriceHandler (
        val priceParsers: List<PriceParser>,
        val userGoodsRepo: UserGoodsRepo
) {
    fun handle(userGoods: UserGoods){
        userGoods.goods.forEach { good ->
            val storeName = good.store.name
            val parser = Optional.ofNullable(
                    priceParsers.find {
                                        it.javaClass.simpleName.startsWith(storeName, true) })
                    .orElse(priceParsers.find {
                        it.javaClass.simpleName.equals("HtmlPriceParser", true) })

            val priceValue =  parser.getCurrentPrice(good)
            val price = Price(good = good, price = priceValue)
            userGoods.price.add(price)

        }
        userGoodsRepo.save(userGoods)
    }
}