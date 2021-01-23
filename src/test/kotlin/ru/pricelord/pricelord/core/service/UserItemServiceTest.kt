package ru.pricelord.pricelord.core.service

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.core.db.model.User
import ru.pricelord.pricelord.core.db.model.UserItem
import ru.pricelord.pricelord.core.db.repository.UserItemRepository
import ru.pricelord.pricelord.core.db.repository.UserRepository
import ru.pricelord.pricelord.core.errors.UserNotFoundException
import ru.pricelord.pricelord.getObject
import ru.pricelord.pricelord.input.rest.model.request.AddUserItemRequest
import ru.pricelord.pricelord.input.rest.model.response.AddUserItemResponse
import java.util.*

internal class UserItemServiceTest {

    private val userItemRepository = mock<UserItemRepository>()
    private val userRepository = mock<UserRepository>()
    private val itemService = mock<ItemService>()
    private val linkService = mock<LinkService>()
    private val userItemService = UserItemService(userItemRepository, userRepository, itemService, linkService)

    @Test
    fun findItemsByUserId() {
        val userId = "userId"
        userItemService.findItemsByUserId(userId)

        verify(userItemRepository, times(1)).findByUserId(userId)
    }

    @Test
    fun `saveUserItem without saving new item`() {
        val request = getObject<AddUserItemRequest>("userItem/request/add_userItem_request1.json")
        val item = getObject<Item>("item/saved_item_mvideo.json")
        val userId = "userId"
        val link = "mvideo.ru/item"
        val expectedUserItem = getObject<UserItem>("userItem/model/userItem_userId_after_saving.json")
        val expectedResponse = getObject<AddUserItemResponse>("userItem/response/add_userItem_response1.json")

        whenever(linkService.formatLink(request.link)).thenReturn(link)
        whenever(itemService.findItemByLink(link)).thenReturn(item)
        whenever(userRepository.findById(userId)).thenReturn(Optional.of(User(userId, "authToken")))
        whenever(userItemRepository.save(expectedUserItem.copy(id = null))).thenReturn(expectedUserItem)

        val response = userItemService.saveUserItem(request)

        assertEquals(expectedResponse, response)

        verify(linkService, times(1)).formatLink(request.link)
        verify(itemService, times(1)).findItemByLink(link)
        verify(userRepository, times(1)).findById(userId)
        verify(userItemRepository, times(1)).save(expectedUserItem.copy(id = null))
    }

    @Test
    fun `saveUserItem with saving new item`() {
        val request = getObject<AddUserItemRequest>("userItem/request/add_userItem_request1.json")
        val item = getObject<Item>("item/saved_item_mvideo.json")
        val userId = "userId"
        val link = "mvideo.ru/item"
        val expectedUserItem = getObject<UserItem>("userItem/model/userItem_userId_after_saving.json")
        val expectedResponse = getObject<AddUserItemResponse>("userItem/response/add_userItem_response1.json")

        whenever(linkService.formatLink(request.link)).thenReturn(link)
        whenever(itemService.findItemByLink(link)).thenReturn(null)
        whenever(itemService.saveItem(item.copy(id = null, store = null, lastPrice = null))).thenReturn(item)
        whenever(userRepository.findById(userId)).thenReturn(Optional.of(User(userId, "authToken")))
        whenever(userItemRepository.save(expectedUserItem.copy(id = null))).thenReturn(expectedUserItem)

        val response = userItemService.saveUserItem(request)

        assertEquals(expectedResponse, response)

        verify(linkService, times(1)).formatLink(request.link)
        verify(itemService, times(1)).findItemByLink(link)
        verify(itemService, times(1)).saveItem(item.copy(id = null, store = null, lastPrice = null))
        verify(userRepository, times(1)).findById(userId)
        verify(userItemRepository, times(1)).save(expectedUserItem.copy(id = null))
    }

    @Test
    fun `saveUserItem throws UserNotFoundException`() {
        val request = getObject<AddUserItemRequest>("userItem/request/add_userItem_request1.json")
        val item = getObject<Item>("item/saved_item_mvideo.json")
        val userId = "userId"
        val link = "mvideo.ru/item"

        whenever(linkService.formatLink(request.link)).thenReturn(link)
        whenever(itemService.findItemByLink(link)).thenReturn(item)
        whenever(userRepository.findById(userId)).thenThrow(UserNotFoundException("msg"))

        assertThrows(UserNotFoundException::class.java) {
            userItemService.saveUserItem(request)
        }
    }
}