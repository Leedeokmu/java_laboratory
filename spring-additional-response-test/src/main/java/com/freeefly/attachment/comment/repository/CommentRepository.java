package com.freeefly.attachment.comment.repository;

import com.freeefly.attachment.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getByBoardId(Long boardId);
}
