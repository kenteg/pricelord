package ru.pricelord.pricelord.bot.service

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.pricelord.pricelord.bot.chatId
import ru.pricelord.pricelord.bot.config.TELEGRAM_BOT
import ru.pricelord.pricelord.bot.service.ButtonData.ADD_ITEM
import ru.pricelord.pricelord.bot.service.ButtonData.EDIT_ITEM
import ru.pricelord.pricelord.bot.text
import ru.pricelord.pricelord.bot.userId
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.core.db.model.OperationType
import ru.pricelord.pricelord.core.db.model.OperationType.*
import ru.pricelord.pricelord.core.db.model.UserItem
import ru.pricelord.pricelord.core.service.*

@Component
class MessageHandler(
    private val userService: UserService,
    private val userItemService: UserItemService,
    private val itemService: ItemService,
    private val priceService: PriceService,
    private val activeOperationService: ActiveOperationService
) {
    lateinit var executeMessage: (message: SendMessage) -> Unit
    val addButton =  InlineKeyboardButton("Add item").apply { callbackData = ADD_ITEM.name }
    val editButton =  InlineKeyboardButton("Edit item").apply { callbackData = EDIT_ITEM.name}

    fun handleStart(update: Update) {
        val chatId = update.chatId()
        val userId = update.userId()
        val user = userService.getUserById(userId)

        if (user == null) {
            firstVisit(userId, chatId)
        } else {
            handleUserItems(userId, chatId)
        }
    }

    fun handleUserItems(userId: String, chatId: String) {
        val userItems = userItemService.findItemsByUserId(userId)
        if (userItems.isEmpty()) {
            sendEmptyUserItems(chatId)
        } else {
            sendUserItems(userItems, chatId)
        }
    }

    private fun sendUserItems(userItems: List<UserItem>, chatId: String) {
        val itemIds = userItems.map { it.itemId }

        val items = itemService.findByIds(itemIds)
        val itemIdToItem = items.associateBy { it.id }

        val prices = priceService.findPriceByIds(
            items
                .filter { it.lastPriceId != null }
                .map { it.lastPriceId!! }
        )
        val priceIdToPrice = prices.associateBy { it.id }

        var messageText = ""

        userItems.forEachIndexed { index, it ->
            val item = itemIdToItem[it.itemId]
            val lastPrice = priceIdToPrice[item!!.lastPriceId]

            messageText += """
                    |${index + 1}. ${it.name}
                    |Price: ${lastPrice?.price ?: ""}
                    |Link: ${item.link}
                """.trimIndent()
        }

        messageText = """Our monitored items:
                |$messageText
            """.trimMargin()

        val inlineKeyBoard = InlineKeyboardMarkup()

        val row = mutableListOf(addButton, editButton)
        val rows = mutableListOf(row)
        inlineKeyBoard.keyboard = rows

        val message = SendMessage().apply {
            this.chatId = chatId
            this.text = messageText
        }
        message.replyMarkup = inlineKeyBoard
        executeMessage(message)
    }

    private fun firstVisit(userId: String, chatId: String) {
        userService.saveUser(id = userId, extSystem = TELEGRAM_BOT)
        sendEmptyUserItems(chatId)
    }

    private fun sendEmptyUserItems(chatId: String) {
        val inlineKeyBoard = InlineKeyboardMarkup()

        val row = mutableListOf(addButton)
        val rows = mutableListOf(row)
        inlineKeyBoard.keyboard = rows

        val message = SendMessage().apply {
            this.chatId = chatId
            this.text = "You have no items. Add new one to start monitoring"
        }
        message.replyMarkup = inlineKeyBoard
        executeMessage(message)
    }

    fun handleAddItem(update: Update) {
        //save activeOperation ADD_ITEM
    }

    fun handleEditItem(update: Update) {

    }

    fun handleMessage(update: Update) {
        val userId = update.userId()
        val chatId = update.chatId()
        val activeOperation = activeOperationService.getActiveOperation(userId) ?: return

        when(activeOperation.operation) {
            OperationType.ADD_ITEM -> {
                itemService.saveItem(Item(link = update.text()))
                //save new userItem
                activeOperationService.saveActiveOperation(ADD_ITEM_LINK, userId)
                sendMessage(update.chatId(), "Input item name:")
            }
            ADD_ITEM_LINK -> {
                //get saved user item without name
                //update name
                //resave
                //delete activeOperation
                //send message
            }
            EDIT_ITEM_LINK -> {

            }
            EDIT_ITEM_NAME -> {

            }
            DELETE_ITEM -> {

            }
            else -> throw RuntimeException("Unavailable operation type: ${activeOperation.operation}")
        }
    }

    private fun sendMessage(chatId: String, text: String) {
        val message = SendMessage().apply {
            this.chatId = chatId
            this.text = text
        }
        executeMessage(message)
    }

}