package ru.pricelord.pricelord.core.db.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.core.db.model.UserItem
import ru.pricelord.pricelord.getObject

@DataMongoTest
@ExtendWith(SpringExtension::class)
internal class UserItemRepositoryTest {
    @Autowired
    lateinit var userItemRepository: UserItemRepository

    @Autowired
    lateinit var itemRepository: ItemRepository

    lateinit var userItem1: UserItem

    @BeforeEach
    fun setup() {
        userItemRepository.deleteAll()

        var item = getObject<Item>("item/item_mvideo_8888.json")
        var item2 = getObject<Item>("item/item_mvideo_9999.json")
        item = itemRepository.save(item)
        item2 = itemRepository.save(item2)

        userItem1 = getObject("userItem/model/userItem_user1_1.json")
        userItem1.item = item
        val userItem2 = getObject<UserItem>("userItem/model/userItem_user2.json")
        userItem2.item = item2

        userItemRepository.save(userItem1)
        userItemRepository.save(userItem2)
    }

    @Test
    fun `findByUserId`() {
        val user1Items = userItemRepository.findByUserId("1")
        val noUserItem = userItemRepository.findByUserId("noUser")

        assertEquals(userItem1.userId, user1Items[0].userId)
        assertEquals(0, noUserItem.size)

    }
}
