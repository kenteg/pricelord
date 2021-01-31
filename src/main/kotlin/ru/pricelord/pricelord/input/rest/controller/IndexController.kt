package ru.pricelord.pricelord.input.rest.controller


import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import ru.pricelord.pricelord.core.db.repository.UserItemRepository
import ru.pricelord.pricelord.core.db.repository.UserRepository

@Controller
class IndexController(
        val userRepository: UserRepository,
        val userItemsRepo: UserItemRepository
        ) {

    @GetMapping("/")
    fun getIndexPage(model: Model, authentication: OAuth2AuthenticationToken): String? {
        val oidcUser = authentication.principal as DefaultOidcUser
        val user = userRepository.findByEmail(oidcUser.email).get()

        val userItems = userItemsRepo.findByUserId(user.id!!)

        model.addAttribute("user", user)
        model.addAttribute("userItems", userItems)
        return "index"
    }
}