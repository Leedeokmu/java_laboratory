package com.freeefly.attachment.comment.repository;

import com.freeefly.attachment.comment.model.Comment;
import com.freeefly.board.model.Board;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class CommentRepository  {

    private long generatedId = 0L;
    private Map<Long, Comment> commentMap = new ConcurrentHashMap<>();

    public void saveAll(List<Comment> comments) {
        comments.forEach(comment -> {
            comment.setId(++generatedId);
            commentMap.put(comment.getId(), comment);
        });
    }

    public List<Comment> getByBoardId(Long boardId){
        return commentMap.values().stream()
                .filter(vo -> vo.getBoardId().equals(boardId))
                .collect(Collectors.toList());
    };
}
