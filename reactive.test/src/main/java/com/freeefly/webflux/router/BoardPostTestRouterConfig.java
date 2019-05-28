//package com.freeefly.webflux.router;
//
//import com.freeefly.webflux.handler.BoardPostHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.config.EnableWebFlux;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//import org.springframework.web.reactive.function.server.RequestPredicates;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//
//@Configuration
//public class BoardPostTestRouterConfig implements WebFluxConfigurer {
//
//    @Bean
//    public RouterFunction<ServerResponse> boardPostRoutes(BoardPostHandler boardPostHandler){
//        return RouterFunctions
//                .route(GET("/board-post/list")
//                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
//                        boardPostHandler::list);
//    }
//
//}
