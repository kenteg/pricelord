package ru.pricelord.pricelord.config


import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesRegistrationAdapter
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames
import java.util.ArrayList

@Configuration
@EnableConfigurationProperties(OAuth2ClientProperties::class)
class SecurityConfig: WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/resources/**", "/login**", "/register").permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()

//            .and().formLogin().loginPage("/login")
//            .defaultSuccessUrl("/notes").failureUrl("/login?error").permitAll()
//            .and().logout().logoutSuccessUrl("/").permitAll()
    }


    @Bean
    fun clientRegistrationRepository(properties:OAuth2ClientProperties): ClientRegistrationRepository {
        val registrations: List<ClientRegistration> = ArrayList(
            OAuth2ClientPropertiesRegistrationAdapter.getClientRegistrations(properties).values
        )
        return InMemoryClientRegistrationRepository(registrations)
    }
//
//  @ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.google")
//    fun googleClientRegistration():ClientRegistration  {
//        return ClientRegistration.withRegistrationId("google")
//            .build()
//    }
}