package com.freeefly;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InitDatabase {
    @Bean
    CommandLineRunner init(MongoOperations operations){
        return args -> {
            operations.dropCollection(Image.class);

            operations.insert(new Image(UUID.randomUUID().toString(), "test1.jpg"));
            operations.insert(new Image(UUID.randomUUID().toString(), "test2.jpg"));
            operations.insert(new Image(UUID.randomUUID().toString(), "test3.jpg"));
            operations.insert(new Image(UUID.randomUUID().toString(), "test4.jpg"));

            operations.findAll(Image.class).forEach(image -> {
                System.out.println(image.toString());
            });
        };
    }
}
