package com.freeefly.controller;

import com.freeefly.aspect.Attach;
import com.freeefly.dto.BoardDto;
import com.freeefly.dto.BoardDtoConverter;
import com.freeefly.model.Board;
import com.freeefly.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardDtoConverter boardDtoConverter;

    @Attach
    @GetMapping("/{id}")
    public BoardDto getOne(@PathVariable("id") Board board) {
        return boardDtoConverter.convert(board);
    }

}
