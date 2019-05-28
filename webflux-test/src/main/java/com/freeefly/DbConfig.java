package com.freeefly;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
//@ConditionalOnProperty(prefix = "spring.data.mongodb.reactive-repositories", name = "enabled",havingValue = "true", matchIfMissing = true)
//@Import(MongoReactiveRepositoriesAutoConfiguration.class)
@EnableReactiveMongoRepositories
public class DbConfig {

}
