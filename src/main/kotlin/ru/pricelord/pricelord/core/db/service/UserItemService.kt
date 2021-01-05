package ru.pricelord.pricelord.core.db.service

import org.springframework.stereotype.Service
import ru.pricelord.pricelord.core.db.errors.UserNotFoundException
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.core.db.model.UserItem
import ru.pricelord.pricelord.core.db.repository.UserItemRepository
import ru.pricelord.pricelord.core.db.repository.UserRepository
import ru.pricelord.pricelord.input.web.model.request.UserItemRequest

@Service
class UserItemService(
        private val userItemRepository: UserItemRepository,
        private val userRepository: UserRepository,
        private val itemService: ItemService,
        private val linkService: LinkService
) {
    fun findItemsByUserId(userId: Long): List<UserItem> =
            userItemRepository.findByUserId(userId)

    fun saveUserItem(userItemRequest: UserItemRequest): UserItem {
        val formattedLink = linkService.formatLink(userItemRequest.link)
        val items = itemService.findItemByLink(formattedLink) ?: itemService.saveItem(Item(link = formattedLink))

        val userItems = UserItem(
                user = userRepository.findById(userItemRequest.userId).orElseThrow {
                    UserNotFoundException("User with id ${userItemRequest.userId} not found")
                },
                isNeedNotification = userItemRequest.isNeedNotification,
                item = items
        )

        return userItemRepository.saveAndFlush(userItems)
    }
}