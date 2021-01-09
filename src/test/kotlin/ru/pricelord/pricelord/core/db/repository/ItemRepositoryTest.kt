package ru.pricelord.pricelord.core.db.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
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

    @Test
    fun `save item`() {
        val item = getObject<Item>("item/item1.json")
        val savedItem = itemRepository.save(item)

        assertEquals(item.link, savedItem.link)
        assertNotNull(savedItem.id)
    }

    @Test
    fun `find by id`() {
//        val invoice = getObject<Invoice>("invoice/model/invoice1.json")
//        invoiceRepository.save(invoice)
//
//        val invoiceDb = invoiceRepository.findByInvoiceId("111")
//
//        assertNotNull(invoiceDb)
    }

}
