package ru.pricelord.pricelord.input.rest.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.pricelord.pricelord.core.db.model.UserItem
import ru.pricelord.pricelord.core.service.UserItemService
import ru.pricelord.pricelord.input.rest.model.request.AddUserItemRequest
import ru.pricelord.pricelord.input.rest.model.response.AddUserItemResponse

@RestController
@RequestMapping("/v1/user-item")
class UserItemController(
        val userItemService: UserItemService
) {

    @CrossOrigin(origins = ["*", "http://localhost:8080"])
    @GetMapping
    fun showAllUserItems(@RequestParam userId: String): List<UserItem> {
        return userItemService.findItemsByUserId(userId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addUserItem(@RequestBody addUserItemRequest: AddUserItemRequest): AddUserItemResponse {
        return userItemService.saveUserItem(addUserItemRequest)
    }
}