package ru.pricelord.pricelord.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource


@Configuration
class DbConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "db.datasource")
    fun primaryDataSource(): HikariDataSource = HikariDataSource()
}