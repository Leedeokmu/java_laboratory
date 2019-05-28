//package com.freeefly.webflux.handler;
//
//
//import com.freeefly.webflux.model.BoardPost;
//import com.freeefly.webflux.repository.BoardPostRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Component
//public class BoardPostHandler {
//    private final BoardPostRepository boardPostRepository;
//
//    public Mono<ServerResponse> list(ServerRequest request){
//        List<BoardPost> posts = boardPostRepository.findAll();
//        Flux<BoardPost> boardPostFlux = Flux.fromIterable(posts);
//        return ServerResponse.ok().body(boardPostFlux, BoardPost.class);
//    }
//
//}
