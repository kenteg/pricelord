package ru.pricelord.pricelord.input.web.controller

import org.springframework.web.bind.annotation.*
import ru.pricelord.pricelord.core.db.model.UserItem
import ru.pricelord.pricelord.core.db.service.UserItemService
import ru.pricelord.pricelord.input.web.model.request.UserItemRequest

@RestController("/v1")
class UserItemController(
        val userItemService: UserItemService
) {

    @CrossOrigin(origins = ["*", "http://localhost:8080"])
    @GetMapping("/user-item")
    fun showAllUserItems(@RequestParam userId: String): List<UserItem> {
        return userItemService.findItemsByUserId(userId)
    }

    @PostMapping("/user-item")
    fun addUserItem(@RequestBody userItemRequest: UserItemRequest): UserItem {
        return userItemService.saveUserItem(userItemRequest)
    }
}