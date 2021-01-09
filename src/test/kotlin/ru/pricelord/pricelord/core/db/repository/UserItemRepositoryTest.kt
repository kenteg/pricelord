package ru.pricelord.pricelord.core.db.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.pricelord.pricelord.core.db.model.UserItem
import ru.pricelord.pricelord.getObject

@DataMongoTest
@ExtendWith(SpringExtension::class)
internal class UserItemRepositoryTest {
    @Autowired
    lateinit var userItemRepository: UserItemRepository

    lateinit var userItem1: UserItem

    @BeforeEach
    fun setup() {
        userItemRepository.deleteAll()
        userItem1 = getObject("userItem/userItem_user1.json")
        val userItem2 = getObject<UserItem>("userItem/userItem_user2.json")

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
