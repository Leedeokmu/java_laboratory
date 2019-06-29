package com.freeefly.dto;

import com.freeefly.attachment.writer.model.Writer;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WriterDtoConverter implements Converter<Writer, WriterDto> {
    @Override
    public WriterDto convert(@NonNull Writer writer) {
        WriterDto writerDto = new WriterDto();
        writerDto.setId(writer.getId());
        writerDto.setUsername(writer.getUsername());
        writerDto.setEmail(writer.getEmail());
        return writerDto;
    }
}
