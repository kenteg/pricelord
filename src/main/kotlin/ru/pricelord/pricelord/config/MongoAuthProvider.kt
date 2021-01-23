package ru.pricelord.pricelord.config

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import ru.pricelord.pricelord.core.db.repository.UserRepository

@Component
class MongoAuthProvider: AuthenticationProvider {

    lateinit var userRepo: UserRepository

    override fun authenticate(authentication: Authentication?): Authentication {
        val clientReg = (authentication as OAuth2LoginAuthenticationToken).clientRegistration
        val userInfoEndpointUri = clientReg
                .providerDetails
                .userInfoEndpoint
                .uri
        if (!userInfoEndpointUri.isEmpty()) {
            val restTemplate = RestTemplate()
            val headers = HttpHeaders()
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " +
                    authentication.authorizationExchange.authorizationResponse.code)
            val entity = HttpEntity("", headers)
            val response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, MutableMap::class.java)
            val userAttributes = response.body!!


        }
        return authentication
    }

        override fun supports(authentication: Class<*>?): Boolean {
            return authentication == OAuth2LoginAuthenticationToken::class.java
        }


    }