package ru.pricelord.pricelord.core.db.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import ru.pricelord.pricelord.core.db.errors.StoreNotFoundException
import ru.pricelord.pricelord.core.db.model.Goods
import ru.pricelord.pricelord.core.db.model.Store
import ru.pricelord.pricelord.core.db.repository.GoodsRepository
import ru.pricelord.pricelord.core.db.repository.StoreRepository

@Service
class GoodsService(
        private val goodsRepository: GoodsRepository,
        private val storeRepository: StoreRepository
) {

    private val log = KotlinLogging.logger {}

    fun linkStoreToGoods() {
        val goods = goodsRepository.findGoodsWithoutStore()
        goods.forEach {
            try {
                val store = findStore(it)

                it.store = store ?: throw StoreNotFoundException("Store not found for goods: ${it.id} - ${it.link}")

                goodsRepository.saveAndFlush(it)
            } catch (ex: Throwable) {
                log.error("Error while updating store for goods: ${it.id} - ${it.link}", ex)
            }

        }
    }

    fun findStore(goods: Goods): Store? {
        val storeLink = goods.link.substringBefore("/")
        return storeRepository.findByLink(storeLink)
    }

    fun findGoodsByLink(link: String): Goods? = goodsRepository.findByLink(link)

    fun saveGoods(goods: Goods): Goods = goodsRepository.saveAndFlush(goods)
}