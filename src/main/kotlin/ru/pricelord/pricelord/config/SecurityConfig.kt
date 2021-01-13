package ru.pricelord.pricelord.config

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


class SecurityConfig: WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/resources/**", "/", "/login**", "/registration").permitAll()
            .anyRequest().authenticated()
            .and().formLogin().loginPage("/login")
            .defaultSuccessUrl("/notes").failureUrl("/login?error").permitAll()
            .and().logout().logoutSuccessUrl("/").permitAll()
    }
}