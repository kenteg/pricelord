package ru.pricelord.pricelord.core.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.core.db.model.UserItem
import ru.pricelord.pricelord.core.db.repository.UserItemRepository
import ru.pricelord.pricelord.core.db.repository.UserRepository
import ru.pricelord.pricelord.core.errors.UserNotFoundException
import ru.pricelord.pricelord.input.rest.model.request.AddUserItemRequest
import ru.pricelord.pricelord.input.rest.model.response.AddUserItemResponse

@Service
class UserItemService(
        private val userItemRepository: UserItemRepository,
        private val userRepository: UserRepository,
        private val itemService: ItemService,
        private val linkService: LinkService
) {
    fun findItemsByUserId(userId: String): List<UserItem> =
        userItemRepository.findByUserId(userId)

    @Transactional(propagation = Propagation.REQUIRED)
    fun saveUserItem(addUserItemRequest: AddUserItemRequest): AddUserItemResponse {
        val formattedLink = linkService.formatLink(addUserItemRequest.link)
        val item = itemService.findItemByLink(formattedLink) ?: itemService.saveItem(Item(link = formattedLink))

        val userItems = UserItem(
            userId = userRepository.findById(addUserItemRequest.userId).orElseThrow {
                UserNotFoundException("User with id ${addUserItemRequest.userId} not found")
            }.id!!,
            isNeedNotification = addUserItemRequest.isNeedNotification,
            itemId = item.id!!,
            name = addUserItemRequest.name
        )

        val savedUserItem = userItemRepository.save(userItems)

        return AddUserItemResponse(
            userId = savedUserItem.userId,
            id = savedUserItem.id!!,
            name = savedUserItem.name,
            itemId = item.id,
            link = item.link,
            isNeedNotification = savedUserItem.isNeedNotification
        )
    }
}