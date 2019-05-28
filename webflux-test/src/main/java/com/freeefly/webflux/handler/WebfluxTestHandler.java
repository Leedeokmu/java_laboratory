package com.freeefly.webflux.handler;

import com.freeefly.webflux.model.WebfluxTestModel;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class WebfluxTestHandler {
    public Mono<ServerResponse> getWebfluxModel(ServerRequest request){
        Mono<WebfluxTestModel> testMono = Mono.just(
                WebfluxTestModel.
                    builder().
                    id(123L).
                    desc("test description").
                    build()
        );
        return ServerResponse.ok().body(testMono, WebfluxTestModel.class);
    }
}
