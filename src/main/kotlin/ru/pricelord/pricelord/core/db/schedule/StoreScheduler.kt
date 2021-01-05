package ru.pricelord.pricelord.core.db.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.pricelord.pricelord.core.db.service.GoodsService

@Component
class StoreScheduler(
        private val goodsService: GoodsService
) {
    @Scheduled(fixedDelayString = "\${scheduler.linkStoreToGoods.delay}")
    fun linkStoreToGoods() {
        goodsService.linkStoreToGoods()
    }
}