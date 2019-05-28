package com.freeefly;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ImageRepository extends ReactiveMongoRepository<Image, String> {
    Mono<Image> findByName(String name);
    Mono<Void> deleteByName(String name);
}
