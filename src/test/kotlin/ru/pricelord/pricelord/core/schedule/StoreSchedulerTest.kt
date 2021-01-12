package ru.pricelord.pricelord.core.schedule

import com.nhaarman.mockito_kotlin.*
import org.junit.jupiter.api.Test
import ru.pricelord.pricelord.core.service.ItemService

internal class StoreSchedulerTest {

    private val itemService = mock<ItemService>()
    private val scheduler = StoreScheduler(itemService)

    @Test
    fun linkItemToStore() {
        doNothing().whenever(itemService).linkItemToStore()

        scheduler.linkItemToStore()

        verify(itemService, times(1)).linkItemToStore()
    }
}