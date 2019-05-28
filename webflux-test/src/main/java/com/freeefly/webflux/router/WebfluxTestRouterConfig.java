package com.freeefly.webflux.router;

import com.freeefly.webflux.handler.WebfluxTestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class WebfluxTestRouterConfig implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> webFluxTestRoutes(WebfluxTestHandler webfluxTestHandler){
        return RouterFunctions.
                route(GET("/webflux-test"), webfluxTestHandler::getWebfluxModel);
    }

}
