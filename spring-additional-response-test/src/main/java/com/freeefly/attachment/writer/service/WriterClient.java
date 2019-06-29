package com.freeefly.attachment.writer.service;

import com.freeefly.dto.WriterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="writer-api", url="localhost:8000")
public interface WriterClient {
    @GetMapping("/users/{writerId}")
    WriterDto getWriter(@PathVariable("writerId") Long writerId);
}
