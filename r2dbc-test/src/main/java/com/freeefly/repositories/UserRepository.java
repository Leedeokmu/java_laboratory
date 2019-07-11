package com.freeefly.repositories;

import com.freeefly.model.Users;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends R2dbcRepository<Users, Long> {
}
