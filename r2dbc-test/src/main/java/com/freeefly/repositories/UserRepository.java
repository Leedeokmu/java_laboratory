package com.freeefly.repositories;

import com.freeefly.model.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface UserRepository extends R2dbcRepository<Users, Long> {

    @Query("SELECT * FROM users")
    Flux<Users> findAll(Pageable page);

}
