package com.freeefly.dto;

import com.freeefly.attachment.comment.model.Comment;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoConverter implements Converter<Comment, CommentDto> {
    @Override
    public CommentDto convert(@NonNull Comment value) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(value.getId());
        commentDto.setEmail(value.getEmail());
        commentDto.setBody(value.getBody());

        return commentDto;
    }
}
