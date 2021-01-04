package ru.pricelord.pricelord.input.web.model.request

import ru.pricelord.pricelord.core.db.model.Goods
import ru.pricelord.pricelord.core.db.model.Store

data class GoodsRequest(
        val userId: Long,
        val id: Long,
        val name: String,
        val link: String,
        val isNeedNotification: Boolean,
        val store: Store?
)

fun GoodsRequest.toGoods() = Goods(
        id = this.id,
        name = this.name,
        link = this.link,
        store = this.store
)