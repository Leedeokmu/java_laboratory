package com.freeefly.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
public class Users {
    @Id
    private Long id;
    private String email;
    private String name;

    public Users(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
