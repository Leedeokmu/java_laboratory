package com.freeefly.attachment.comment.service;

import com.freeefly.dto.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="comment-api", url="localhost:8000")
public interface CommentClient {
    @GetMapping("/comments")
    List<CommentDto> getComments(@RequestParam("boardId") Long boardId);
}
