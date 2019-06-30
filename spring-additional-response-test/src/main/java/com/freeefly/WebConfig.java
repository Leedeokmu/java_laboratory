package com.freeefly;

import com.freeefly.board.BoardHandler;
import com.freeefly.interceptor.AttachmentHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@EnableWebFlux
@Configuration
public class WebConfig extends DelegatingWebFluxConfiguration {
    private final BoardHandler boardHandler;
    private final AttachmentHandlerFilter attachmentHandlerFilter;

    @Bean
    public RouterFunction<?> boardRouter() {
        return route(GET("/boards/{id}").and(accept(MediaType.APPLICATION_JSON)), boardHandler::getBoard).filter(attachmentHandlerFilter.filter());
    }





}