package com.freeefly.dto;

import lombok.Data;

@Data
public class WriterDto implements Attachment{
    private Long id;
    private String username;
    private String email;
}
