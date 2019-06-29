package com.freeefly.attachment.writer.service;

import com.freeefly.attachment.writer.model.Writer;
import com.freeefly.attachment.writer.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReadWriterService {
    private final WriterRepository writerRepository;

    public Writer getWriter(Long id){
//        return writerRepository.findById(id);
        return writerRepository.findById(id).get();
    }
}
