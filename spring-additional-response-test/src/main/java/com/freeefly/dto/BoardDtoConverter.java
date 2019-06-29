package com.freeefly.dto;

import com.freeefly.board.model.Board;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BoardDtoConverter implements Converter<Board, BoardDto> {
    @Override
    public BoardDto convert(@NonNull Board board) {
        BoardDto boardDto = new BoardDto();

        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());

        return boardDto;
    }
}
