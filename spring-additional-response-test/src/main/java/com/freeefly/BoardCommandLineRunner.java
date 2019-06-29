package com.freeefly;

import com.freeefly.attachment.comment.model.Comment;
import com.freeefly.attachment.comment.repository.CommentRepository;
import com.freeefly.attachment.writer.model.Writer;
import com.freeefly.attachment.writer.repository.WriterRepository;
import com.freeefly.board.model.Board;
import com.freeefly.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Component
public class BoardCommandLineRunner implements CommandLineRunner {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final WriterRepository  writerRepository;

    @Override
    public void run(String... args) throws Exception {
        // 게시글 샘플 등록
        List<Board> boards = IntStream.rangeClosed(1, 100)
                .mapToObj(n -> new Board("title" + n, "content" + n, (long) n))
                .collect(Collectors.toList());
        boardRepository.saveAll(boards);

        // 게시글-답글 샘플 등록
        List<Comment> comments = boards
                .stream()
                .map(vo -> new Comment("test" + vo.getId() + "@abc.com", "lorem", vo.getId()))
                .collect(Collectors.toList());
        comments.addAll(boards
                .stream()
                .map(vo -> new Comment("test" + vo.getId() + "@abc.com", "lorem", vo.getId()))
                .collect(Collectors.toList()));
        commentRepository.saveAll(comments);

        // 작성자 샘플 등록
        List<Writer> writers = IntStream.rangeClosed(1, 100)
                .mapToObj(n -> new Writer("test" + n, "test" + n + "@abc.com"))
                .collect(Collectors.toList());
        writerRepository.saveAll(writers);
    }
}
