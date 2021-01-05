package ru.pricelord.pricelord.core.db.service

import mu.KotlinLogging
import org.springframework.stereotype.Service
import ru.pricelord.pricelord.core.db.errors.StoreNotFoundException
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.core.db.model.Store
import ru.pricelord.pricelord.core.db.repository.ItemRepository
import ru.pricelord.pricelord.core.db.repository.StoreRepository

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
                val store = findStore(it)

                it.store = store ?: throw StoreNotFoundException("Store not found for item: ${it.id} - ${it.link}")

                itemRepository.saveAndFlush(it)
            } catch (ex: Throwable) {
                log.error("Error while updating store for item: ${it.id} - ${it.link}", ex)
            }

        }
    }

    fun findStore(item: Item): Store? {
        val storeLink = item.link.substringBefore("/")
        return storeRepository.findByLink(storeLink)
    }

    fun findItemByLink(link: String): Item? = itemRepository.findByLink(link)

    fun saveItem(item: Item): Item = itemRepository.saveAndFlush(item)
}