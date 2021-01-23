package ru.pricelord.pricelord.bot

import org.telegram.telegrambots.meta.api.objects.Update

fun Update.chatId(): String = this.message.chatId.toString()
fun Update.text(): String = this.message.text
fun Update.userId() = this.message.from.id.toString()