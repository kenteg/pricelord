package ru.pricelord.pricelord.bot.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import ru.pricelord.pricelord.bot.config.TELEGRAM_BOT
import ru.pricelord.pricelord.core.service.ItemService
import ru.pricelord.pricelord.core.service.PriceService
import ru.pricelord.pricelord.core.service.UserItemService
import ru.pricelord.pricelord.core.service.UserService

@Component
class LearnerBot(
        private val botOptions: DefaultBotOptions,
        @Value("\${bot.token}")
        private val botToken: String,
        @Value("\${bot.name}")
        private val botName: String,
        private val userService: UserService,
        private val userItemService: UserItemService,
        private val itemService: ItemService,
        private val priceService: PriceService
) : TelegramLongPollingBot(botOptions) {

    override fun onUpdateReceived(update: Update?) {
        if (update!!.hasCallbackQuery()) {
            handleCallback(update)
        } else {
            handleMessage(update)
        }
    }

    private fun handleMessage(update: Update) {
        val chatId = update.message.chatId.toString()
        val message = update.message.text
        val userId = update.message.from.id.toString()

        if (message == "/start") {
            var user = userService.getUserById(userId)
            if (user == null) {
                user = userService.saveUser(id = userId, extSystem = TELEGRAM_BOT)
            } else {
                val userItems = userItemService.findItemsByUserId(userId)
                if (userItems.isEmpty()) {
                    sendMessage(chatId, "You have no monitored prices. Add new one")
                    //send keys
                } else {
                    val itemIds = userItems.map { it.itemId }

                    val items = itemService.findByIds(itemIds)
                    val itemIdToItem = items.associateBy { it.id }

                    val prices = priceService.findPriceByIds(items.filter { it.lastPriceId != null }.map { it.lastPriceId!! })
                    val priceIdToPrice = prices.associateBy { it.id }

                    sendMessage(chatId, "Our monitored prices:")

                    userItems.forEach {
                        val item = itemIdToItem[it.itemId]
                        val lastPrice = priceIdToPrice[item!!.lastPriceId]

                        sendMessage(
                                chatId = chatId,
                                text = "${it.name} - ${lastPrice?.price ?: ""} - ${item.link}"
                        )


//                        val userItemResponse = UserItemResponse(
//                                id = it.id,
//                                name = it.name,
//                                isNeedNotification = it.isNeedNotification,
//                                link = item.link,
//                                lastPrice = lastPrice?.price
//                        )
                    }
                }
            }


//            if (dbService.isChatIdExists(chatId) && dbService.isHasActiveTest(chatId)) {
//                val activeTest = dbService.findActiveCourse(chatId)
//                sendMessage(chatId, "Please complete existed test: $activeTest")
//            } else {
//                val availableCourses = dbService.getAvailableCourses(chatId)
            sendInlineKeyBoard(chatId)
//            }
        } else {
            print(update)
        }
    }

    fun sendInlineKeyBoard(chatId: String) {
        val inlineKeyBoard = InlineKeyboardMarkup()
        val button1 = InlineKeyboardButton("button1")
        button1.callbackData = "button1"

        val button2 = InlineKeyboardButton("button2")
        button2.callbackData = "button2"
        val row1 = mutableListOf(button1)
        val row2 = mutableListOf(button2)
        val rows = mutableListOf(row1, row2)
        inlineKeyBoard.keyboard = rows

        val message = SendMessage().apply {
            this.chatId = chatId
            this.text = "NotInlineKeyboard"
        }
        message.replyMarkup = inlineKeyBoard
        execute(message)
    }

    private fun sendKeyBoard(chatId: String) {
        val replyKeyboardMarkup = ReplyKeyboardMarkup()
        val rows: MutableList<KeyboardRow> = mutableListOf()
        val row = KeyboardRow()
        val keyboardButton = KeyboardButton("Button 1")
        row.add(keyboardButton)
        rows.add(row)
        replyKeyboardMarkup.keyboard = rows
        replyKeyboardMarkup.oneTimeKeyboard = false
        sendMessage(chatId, "Hi! Choose course:")
        val message = SendMessage().apply {
            this.chatId = chatId
            this.text = "NotInlineKeyboard"
        }
        message.replyMarkup = replyKeyboardMarkup
        message.enableMarkdown(true)
        execute(message)
    }

    private fun sendMessage(chatId: String, text: String) {
        val message = SendMessage().apply {
            this.chatId = chatId
            this.text = text
        }
        execute(message)
    }

    private fun handleCallback(update: Update) {
        println(update)
        // Update{updateId=506593120, message=null, inlineQuery=null, chosenInlineQuery=null, callbackQuery=CallbackQuery{id='1091420097462514362', from=User{id=254116043, firstName='Иван', isBot=false, lastName='Головащенко', userName='franchini', languageCode='ru', canJoinGroups=null, canReadAllGroupMessages=null, supportInlineQueries=null}, message=Message{messageId=123, from=User{id=1119065088, firstName='Brain Builder', isBot=true, lastName='null', userName='BrainBuilderBot', languageCode='null', canJoinGroups=null, canReadAllGroupMessages=null, supportInlineQueries=null}, date=1591306689, chat=Chat{id=254116043, type='private', title='null', firstName='Иван', lastName='Головащенко', userName='franchini', allMembersAreAdministrators=null, photo=null, description='null', inviteLink='null', pinnedMessage=null, stickerSetName='null', canSetStickerSet=null, permissions=null, slowModeDelay=null}, forwardFrom=null, forwardFromChat=null, forwardDate=null, text='NotInlineKeyboard', entities=null, captionEntities=null, audio=null, document=null, photo=null, sticker=null, video=null, contact=null, location=null, venue=null, animation=null, pinnedMessage=null, newChatMembers=null, leftChatMember=null, newChatTitle='null', newChatPhoto=null, deleteChatPhoto=null, groupchatCreated=null, replyToMessage=null, voice=null, caption='null', superGroupCreated=null, channelChatCreated=null, migrateToChatId=null, migrateFromChatId=null, editDate=null, game=null, forwardFromMessageId=null, invoice=null, successfulPayment=null, videoNote=null, authorSignature='null', forwardSignature='null', mediaGroupId='null', connectedWebsite='null', passportData=null, forwardSenderName='null', poll=null, replyMarkup=InlineKeyboardMarkup{inline_keyboard=[[InlineKeyboardButton{text='button1', url='null', callbackData='button1', callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null', pay=null, loginUrl=null}], [InlineKeyboardButton{text='button2', url='null', callbackData='button2', callbackGame=null, switchInlineQuery='null', switchInlineQueryCurrentChat='null', pay=null, loginUrl=null}]]}}, inlineMessageId='null', data='button1', gameShortName='null', chatInstance='4897723192325860895'}, editedMessage=null, channelPost=null, editedChannelPost=null, shippingQuery=null, preCheckoutQuery=null, poll=null, pollAnswer=null}
        val chatId = update.callbackQuery.message.chatId
    }

    override fun getBotUsername(): String {
        return botName
    }

    override fun getBotToken(): String {
        return botToken
    }
}