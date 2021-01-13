package ru.pricelord.pricelord.input.rest.controller

import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.pricelord.pricelord.core.db.model.UserItem
import ru.pricelord.pricelord.core.errors.UserNotFoundException
import ru.pricelord.pricelord.core.service.UserItemService
import ru.pricelord.pricelord.getFileAsString
import ru.pricelord.pricelord.getObject
import ru.pricelord.pricelord.input.rest.model.request.AddUserItemRequest
import ru.pricelord.pricelord.input.rest.model.response.AddUserItemResponse

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@WebMvcTest(UserItemController::class)
class UserItemControllerTest {

    @MockBean
    private lateinit var userItemService: UserItemService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return userItems`() {
        val userId = "userId"
        val userItem1 = getObject<UserItem>("userItem/model/userItem_user1_1.json")
        val userItem2 = getObject<UserItem>("userItem/model/userItem_user1_2.json")
        val expectedResponse = getFileAsString("userItem/response/userItems_response_1.json")

        whenever(userItemService.findItemsByUserId(userId))
            .thenReturn(listOf(userItem1, userItem2))

        mockMvc.perform(
            get("/v1/user-item")
                .param("userId", userId)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(content().json(expectedResponse))

        verify(userItemService).findItemsByUserId(userId)
    }

    @Test
    fun `should add userItem`() {
        val requestPath = "userItem/request/add_userItem_request1.json"
        val addUserItemRequest = getObject<AddUserItemRequest>(requestPath)
        val addUserItemResponse = getObject<AddUserItemResponse>("userItem/response/add_userItem_response1.json")
        val expectedResponse = getFileAsString("userItem/response/add_userItem_response1.json")

        whenever(userItemService.saveUserItem(addUserItemRequest))
            .thenReturn(addUserItemResponse)

        mockMvc.perform(
            post("/v1/user-item")
                .content(getFileAsString(requestPath))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isCreated)
            .andExpect(content().json(expectedResponse))

        verify(userItemService).saveUserItem(addUserItemRequest)
    }

    @Test
    fun `should handle UserNotFoundException`() {
        val requestPath = "userItem/request/add_userItem_request1.json"
        val addUserItemRequest = getObject<AddUserItemRequest>(requestPath)

        doThrow(UserNotFoundException("userId"))
            .whenever(userItemService).saveUserItem(addUserItemRequest)

        mockMvc.perform(
            post("/v1/user-item")
                .content(getFileAsString(requestPath))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isNotFound)
            .andExpect(content().json("""{"errors":[{"code":"USER_NOT_FOUND","message":"userId"}]}""""))
    }

}
