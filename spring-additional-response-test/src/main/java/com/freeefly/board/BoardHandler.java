package com.freeefly.board;

import com.freeefly.aspect.Attachable;
import com.freeefly.attachment.AttachExecutor;
import com.freeefly.attachment.AttachmentTypeHolder;
import com.freeefly.board.repository.BoardRepository;
import com.freeefly.dto.BoardDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;

import static com.freeefly.interceptor.AttachmentHandlerFilter.TARGET_ATTRIBUTE_NAME;


@RequiredArgsConstructor
@Component
public class BoardHandler {
    private final BoardRepository boardRepository;
    private final BoardDtoConverter boardDtoConverter;
    private final AttachExecutor attachExecutor;

    private Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getBoard(ServerRequest request){
        long boardId = Long.valueOf(request.pathVariable("id"));
        // 요청한 attachment 타입 검사
        AttachmentTypeHolder typeHolder = request.attribute(TARGET_ATTRIBUTE_NAME)
                .map(AttachmentTypeHolder.class::cast)
                .orElseGet(() -> new AttachmentTypeHolder(Collections.emptySet()));
        // 게시글 찾아 요청된 attachment 타입에 따라 부가정보 적재
        Mono<Attachable> attachableMono = boardRepository.findById(boardId)
                .map(boardDtoConverter::convert)
                .flatMap(boardDto -> attachExecutor.attach(boardDto, typeHolder));
        // response 세팅
        return attachableMono.flatMap((boardDto -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(boardDto))
                .switchIfEmpty(notFound)));
    }
}
