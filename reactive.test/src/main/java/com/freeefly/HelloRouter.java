package com.freeefly;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class HelloRouter {

    @Bean
    RouterFunction<ServerResponse> helloRouterFunction(HelloHandler helloHandler){
        return RouterFunctions.route(RequestPredicates.GET("/hello"), helloHandler::handleRequest);
    }
}
