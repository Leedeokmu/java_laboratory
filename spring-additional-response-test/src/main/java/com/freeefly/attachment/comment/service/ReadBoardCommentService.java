package com.freeefly.attachment.comment.service;

import com.freeefly.attachment.comment.model.Comment;
import com.freeefly.attachment.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReadBoardCommentService {
    private final CommentRepository commentRepository;

    public List<Comment> getComments(Long boardId){
        return commentRepository.getByBoardId(boardId);
    }
}
