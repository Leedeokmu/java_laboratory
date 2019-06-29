package com.freeefly.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.freeefly.aspect.Attachable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardDto implements Attachable {
    private Long id;
    private String title;
    private String content;
    private Long writerId;

    @Setter(AccessLevel.PRIVATE)
    @JsonIgnore
    private AttachmentWrapper attachmentWrapper = new AttachmentWrapper();
}
