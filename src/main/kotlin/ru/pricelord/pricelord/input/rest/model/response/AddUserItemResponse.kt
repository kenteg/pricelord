package ru.pricelord.pricelord.input.rest.model.response

data class AddUserItemResponse(
    val userId: String,
    val id: String,
    val name: String,
    val itemId: String,
    val link: String,
    val isNeedNotification: Boolean
)