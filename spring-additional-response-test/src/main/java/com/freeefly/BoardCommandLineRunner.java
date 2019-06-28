package com.freeefly;

import com.freeefly.model.Board;
import com.freeefly.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BoardCommandLineRunner implements CommandLineRunner {
    private final BoardRepository boardRepository;

    @Override
    public void run(String... args) throws Exception {
        boardRepository.save(new Board("title1", "content1"));
        boardRepository.save(new Board("title2", "content2"));
        boardRepository.save(new Board("title3", "content3"));
    }
}
