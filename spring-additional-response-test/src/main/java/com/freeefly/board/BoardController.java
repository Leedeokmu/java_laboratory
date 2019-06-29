package com.freeefly.board;

import com.freeefly.aspect.Attach;
import com.freeefly.dto.BoardDto;
import com.freeefly.dto.BoardDtoConverter;
import com.freeefly.board.model.Board;
import com.freeefly.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {
    private final BoardDtoConverter boardDtoConverter;

    @Attach
    @GetMapping("/{id}")
    public BoardDto getOne(@PathVariable("id") Board board) {
        return boardDtoConverter.convert(board);
    }

}
