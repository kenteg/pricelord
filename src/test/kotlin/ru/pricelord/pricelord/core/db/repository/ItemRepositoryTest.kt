package ru.pricelord.pricelord.core.db.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.getObject

@DataMongoTest
@ExtendWith(SpringExtension::class)
internal class ItemRepositoryTest {
    @Autowired
    lateinit var itemRepository: ItemRepository

    lateinit var itemNoLink: Item

    @BeforeEach
    fun setup() {
        itemRepository.deleteAll()
        itemNoLink = getObject<Item>("item/item_link_only.json")
        val item2 = getObject<Item>("item/item_mvideo_8888.json")
        val item3 = getObject<Item>("item/item_mvideo_9999.json")

        itemRepository.save(itemNoLink)
        itemRepository.save(item2)
        itemRepository.save(item3)
    }

    @Test
    fun `findItemsWithoutPrices`() {
        val items = itemRepository.findItemsWithoutPrices()

        assertEquals(1, items.size)
        assertEquals(itemNoLink.link, items[0].link)
    }

    @Test
    fun `findItemsWithoutStore`() {
        val items = itemRepository.findItemsWithoutStore()

        assertEquals(1, items.size)
        assertEquals(itemNoLink.link, items[0].link)
    }

    @Test
    fun `findByLink`() {
        val item = itemRepository.findByLink(itemNoLink.link)

        assertEquals(itemNoLink.link, item!!.link)
    }
}
