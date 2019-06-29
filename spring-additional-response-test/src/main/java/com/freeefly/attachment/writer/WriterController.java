package com.freeefly.attachment.writer;

import com.freeefly.attachment.writer.service.ReadWriterService;
import com.freeefly.dto.WriterDto;
import com.freeefly.dto.WriterDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class WriterController {
    private final ReadWriterService readWriterService;
    private final WriterDtoConverter writerDtoConverter;

    @GetMapping("/{id}")
    public WriterDto getWriter(@PathVariable("id") Long id){
        return writerDtoConverter.convert(readWriterService.getWriter(id));
    }


}
