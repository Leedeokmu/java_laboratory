package com.freeefly.dto;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.freeefly.model.Board;
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
