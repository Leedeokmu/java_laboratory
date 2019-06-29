package com.freeefly.attachment.comment;

import com.freeefly.attachment.comment.model.Comment;
import com.freeefly.attachment.comment.service.ReadBoardCommentService;
import com.freeefly.dto.CommentDto;
import com.freeefly.dto.CommentDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final ReadBoardCommentService readBoardCommentService;
    private final CommentDtoConverter commentDtoConverter;

    @GetMapping
    public List<CommentDto> getComments(@RequestParam("boardId") Long boardId){
        List<Comment> comments = readBoardCommentService.getComments(boardId);
        List<CommentDto> commentDtos = comments
                .stream()
                .map(vo -> commentDtoConverter.convert(vo))
                .collect(Collectors.toList());

        return commentDtos;
    }


}
