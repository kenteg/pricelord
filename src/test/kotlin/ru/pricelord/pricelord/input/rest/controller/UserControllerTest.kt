package ru.pricelord.pricelord.input.rest.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import ru.pricelord.pricelord.core.service.UserService

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@WebMvcTest(UserController::class)
class UserControllerTest {

    @MockBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should create user`() {
//        val invoice1Path = "invoice/model/invoice1.json"
//        val invoice = getObject<Invoice>(invoice1Path)
//        val createInvoiceRequestPath = "invoice/create/request/invoice1.json"
//        val createInvoiceRequest = getObject<CreateInvoiceRequest>(createInvoiceRequestPath)
//        val createInvoiceResponse = getFileAsString("invoice/create/response/invoice1.json")
//
//        whenever(invoiceService.create(createInvoiceRequest))
//            .thenReturn(invoice)
//
//        mockMvc.perform(
//            post("/v1/user")
//                .content(getFileAsString(createInvoiceRequestPath))
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//            .andDo(print())
//            .andExpect(status().isCreated)
//            .andExpect(content().json(createInvoiceResponse))
//
//        verify(invoiceService).create(createInvoiceRequest)
    }

}
