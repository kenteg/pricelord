package ru.pricelord.pricelord.core.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent

import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import ru.pricelord.pricelord.core.db.model.Item
import ru.pricelord.pricelord.core.db.model.Price
import ru.pricelord.pricelord.core.db.model.Store
import ru.pricelord.pricelord.core.db.model.User
import ru.pricelord.pricelord.core.db.model.UserItem
import ru.pricelord.pricelord.core.db.repository.ItemRepository
import ru.pricelord.pricelord.core.db.repository.PriceRepository
import ru.pricelord.pricelord.core.db.repository.StoreRepository
import ru.pricelord.pricelord.core.db.repository.UserItemRepository
import ru.pricelord.pricelord.core.db.repository.UserRepository
import java.math.BigDecimal


@Component
class StartupEvent : ApplicationListener<ApplicationReadyEvent?> {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var storeRepository: StoreRepository

    @Autowired
    lateinit var itemRepository: ItemRepository

    @Autowired
    lateinit var userItemRepository: UserItemRepository

    @Autowired
    lateinit var priceRepository: PriceRepository

    override fun onApplicationEvent(event: ApplicationReadyEvent) {

        var store = Store(name = "mvideo", link = "link", pathToPrice = "pathPrice")
    	store = storeRepository.save(store)

        var price = Price(id = "pr1", price = BigDecimal.TEN)
        price = priceRepository.save(price)

        var item = Item("item1", "https://www.mvideo.ru/products/smartfon-huawei-p40-lite-e-nfc-midnight-black-art-l29n-30050496", store = store, lastPrice = price )
        itemRepository.save(item)

        val user = User("228", "Viktor", "kenteg91@gmail.com")
        userRepository.save(user)

        val userItem = UserItem(userId = "228", name = "govno", item = item, isNeedNotification = true)
        userItemRepository.save(userItem)
    }
}