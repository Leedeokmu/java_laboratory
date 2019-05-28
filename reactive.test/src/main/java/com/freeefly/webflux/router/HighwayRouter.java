package com.freeefly.webflux.router;

import com.freeefly.webflux.handler.HighwayHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class HighwayRouter {

    @Bean
    public RouterFunction<ServerResponse> highwayRoute(HighwayHandler highwayHandler){
        return RouterFunctions
                .route(RequestPredicates.GET("/vehicles")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_STREAM_JSON)),
                        highwayHandler::vehicleDetected);
    }
}
