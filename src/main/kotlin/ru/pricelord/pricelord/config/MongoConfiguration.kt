package ru.pricelord.pricelord.config

import com.mongodb.WriteConcern
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.WriteConcernResolver


@EnableMongoAuditing
@Configuration(proxyBeanMethods = false)
class MongoConfiguration {

    @Bean
    fun writeConcernResolver(): WriteConcernResolver {
        return WriteConcernResolver {
            WriteConcern.ACKNOWLEDGED
        }
    }

//    @Bean
//    fun transactionManager(dbFactory: MongoDatabaseFactory): MongoTransactionManager {
//        return MongoTransactionManager(dbFactory)
//    }

}