package com.freeefly.dto;

import lombok.Data;

@Data
public class CommentDto implements Attachment {
    private Long id;
    private String email;
    private String body;
}
