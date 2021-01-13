package ru.pricelord.pricelord.core.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.pricelord.pricelord.core.service.ItemService

@Component
class StoreScheduler(
        private val itemService: ItemService
) {
    @Scheduled(fixedDelayString = "\${scheduler.linkItemToStore.delay}")
    fun linkItemToStore() {
        itemService.linkItemToStore()
    }
}