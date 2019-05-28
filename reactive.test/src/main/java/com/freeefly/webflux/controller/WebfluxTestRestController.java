package com.freeefly.webflux.controller;

import com.freeefly.webflux.model.WebfluxTestModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/webflux-rest-test")
public class WebfluxTestRestController {

    @GetMapping
    public Mono<WebfluxTestModel> getWebfluxModel(){
        WebfluxTestModel webfluxTestModel = WebfluxTestModel.
                builder().
                id(123L).
                desc("test description").
                build();

        return Mono.just(webfluxTestModel);
    }


}
