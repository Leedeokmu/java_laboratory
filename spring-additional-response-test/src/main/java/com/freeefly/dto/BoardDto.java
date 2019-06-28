package com.freeefly.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.freeefly.aspect.Attach;
import com.freeefly.aspect.Attachable;
import com.freeefly.enumerate.AttachmentType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.EnumMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardDto implements Attachable {
    private Long id;
    private String title;
    private String content;

    @Setter(AccessLevel.PRIVATE)
    @JsonIgnore
    private AttachmentWrapper attachmentWrapper = new AttachmentWrapper();
}
