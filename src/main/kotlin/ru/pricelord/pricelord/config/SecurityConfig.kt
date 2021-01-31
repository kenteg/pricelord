package ru.pricelord.pricelord.config


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService



@Configuration
@EnableConfigurationProperties(OAuth2ClientProperties::class)
@Profile("!test")
class SecurityConfig: WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var googleOidcUserService: OidcUserService

    override fun configure(http: HttpSecurity) {
        http

                .authorizeRequests()
                .antMatchers("/resources/**", "/login**", "/register").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .oidcUserService(googleOidcUserService)

    }
}