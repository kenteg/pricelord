package ru.pricelord.pricelord.input.web.model.request

data class UserGoodsRequest(
        val userId: Long,
        val id: Long,
        val name: String,
        val link: String,
        val isNeedNotification: Boolean
)