package com.freeefly.attachment.comment.model;

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
@Table(name = "comment")
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private Long boardId;
    private String email;
    private String body;

    public Comment(@NonNull String email, @NonNull String body, @NonNull Long boardId) {
        this.email = email;
        this.body = body;
        this.boardId = boardId;
    }

}
