package ru.pricelord.pricelord.config.oidc

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service
import ru.pricelord.pricelord.core.db.model.User
import ru.pricelord.pricelord.core.db.repository.UserRepository


@Service
class GoogleOidcUserService(private val userRepository: UserRepository) : OidcUserService() {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val oidcUser = super.loadUser(userRequest)
        val attributes: Map<*, *> = oidcUser.attributes
        val userInfo = GoogleOAuth2UserInfo(
                id = attributes["sub"] as String,
                name = attributes["name"] as String,
                email = attributes["email"] as String,
                imageUrl = attributes["picture"] as String
        )
        updateUser(userInfo)
        return oidcUser
    }

    private fun updateUser(userInfo: GoogleOAuth2UserInfo) {
        val user = userRepository.findByEmail(userInfo.email).orElse(
                User(
                        email = userInfo.email,
                        imageUrl = userInfo.imageUrl,
                        name = userInfo.name,


                        ))
        logger.debug("Save user: $user.email")
        userRepository.save(user)
    }
}

data class GoogleOAuth2UserInfo(
        var id: String,
        var name: String,
        var email: String,
        var imageUrl: String,
)