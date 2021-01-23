package ru.pricelord.pricelord.input.rest.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService

import org.springframework.beans.factory.annotation.Autowired

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository

import java.util.HashMap

import org.springframework.security.oauth2.client.registration.ClientRegistration

import org.springframework.core.ResolvableType
import org.springframework.ui.Model
import java.util.function.Consumer

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod

import org.springframework.web.client.RestTemplate

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken

import org.springframework.web.bind.annotation.GetMapping





@Controller
class LoginController {

    @Autowired
    private val authorizedClientService: OAuth2AuthorizedClientService? = null

    @GetMapping("/loginSuccess")
    fun getLoginInfo(model: Model, authentication: OAuth2AuthenticationToken): String? {
        val client = authorizedClientService!!.loadAuthorizedClient<OAuth2AuthorizedClient>(authentication.authorizedClientRegistrationId, authentication.name)
        val userInfoEndpointUri = client.clientRegistration
                .providerDetails
                .userInfoEndpoint
                .uri
        if (!userInfoEndpointUri.isEmpty()) {
            val restTemplate = RestTemplate()
            val headers = HttpHeaders()
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.accessToken
                    .tokenValue)
            val entity = HttpEntity("", headers)
            val response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, MutableMap::class.java)
            val userAttributes = response.body!!
            model.addAttribute("name", userAttributes["name"])
        }
        return "loginSuccess"
    }

}