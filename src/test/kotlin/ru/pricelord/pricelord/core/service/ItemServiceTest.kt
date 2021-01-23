package ru.pricelord.pricelord.core.service

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.core.db.model.Store
import ru.pricelord.pricelord.core.db.repository.ItemRepository
import ru.pricelord.pricelord.core.db.repository.StoreRepository
import ru.pricelord.pricelord.getObject

internal class ItemServiceTest {

    private val itemRepository = mock<ItemRepository>()
    private val storeRepository = mock<StoreRepository>()
    private val itemService = ItemService(itemRepository, storeRepository)

    @Test
    fun linkItemToStore() {
        val item1 = getObject<Item>("item/item_mvideo_8888.json")
        val item2 = getObject<Item>("item/item_mvideo_9999.json")
        val store = getObject<Store>("store/mvideo.json")

        whenever(itemRepository.findItemsWithoutStore()).thenReturn(
            listOf(item1.apply { this.store = null }, item2.apply { this.store = null })
        )
        whenever(storeRepository.findByLink("mvideo.ru")).thenReturn(store)
        whenever(itemRepository.save(item1)).thenReturn(item1)
        whenever(itemRepository.save(item2)).thenReturn(item2)

        itemService.linkItemToStore()

        verify(itemRepository, times(1)).save(item1)
        verify(itemRepository, times(1)).save(item2)
    }

    @Test
    fun findItemByLink() {
        val link = "mvideo.ru"
        itemService.findItemByLink(link)

        verify(itemRepository, times(1)).findByLink(link)
    }

    @Test
    fun saveItem() {
        val item1 = getObject<Item>("item/item_mvideo_8888.json")

        whenever(itemRepository.save(item1)).thenReturn(item1)
        itemService.saveItem(item1)

        verify(itemRepository, times(1)).save(item1)
    }
}