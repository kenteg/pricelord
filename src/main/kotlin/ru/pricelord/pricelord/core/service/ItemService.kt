package ru.pricelord.pricelord.core.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.core.db.repository.ItemRepository
import ru.pricelord.pricelord.core.db.repository.StoreRepository
import ru.pricelord.pricelord.core.errors.StoreNotFoundException

@Service
class ItemService(
        private val itemRepository: ItemRepository,
        private val storeRepository: StoreRepository
) {

    private val log = KotlinLogging.logger {}

    fun linkItemToStore() {
        val items = itemRepository.findItemsWithoutStore()
        items.forEach {
            try {
                val storeLink = it.link.substringBefore("/")
                val store = storeRepository.findByLink(storeLink)

                it.storeId =
                    store?.id ?: throw StoreNotFoundException("Store not found for item: ${it.id} - ${it.link}")

                itemRepository.save(it)
            } catch (ex: Throwable) {
                log.error("Error while updating store for item: ${it.id} - ${it.link}", ex)
            }

        }
    }

    fun findItemByLink(link: String): Item? = itemRepository.findByLink(link)

    fun saveItem(item: Item): Item = itemRepository.save(item)
}