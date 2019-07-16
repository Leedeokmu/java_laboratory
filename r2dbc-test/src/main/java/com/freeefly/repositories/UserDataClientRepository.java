package com.freeefly.repositories;

import com.freeefly.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserDataClientRepository {
    private final DatabaseClient databaseClient;


    public Mono<Void> saveAll(List<Users> users){
        return databaseClient.insert()
                .into(Users.class)
                .using(Flux.fromIterable(users))
                .then();
    }

    public Flux<Users> findAll(Pageable page){
        return databaseClient.select()
                .from(Users.class)
                .page(page)
                .fetch()
                .all();
    }

}
