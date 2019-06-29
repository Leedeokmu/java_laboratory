package com.freeefly.attachment.writer.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "writer")
@Entity
public class Writer {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String email;

    public Writer(@NonNull String username, @NonNull String email) {
        this.username = username;
        this.email = email;
    }
}
