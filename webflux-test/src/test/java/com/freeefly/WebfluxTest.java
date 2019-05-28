package com.freeefly;

import com.freeefly.webflux.model.WebfluxTestModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class WebfluxTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test_webfluxRouter(){
        WebfluxTestModel model = webTestClient.get().uri("/webflux-test").exchange()
                .expectStatus().isOk()
                .expectBody(WebfluxTestModel.class)
                .returnResult().getResponseBody();

        assertThat(model.getId()).isEqualTo(123);
        assertThat(model.getDesc()).isEqualTo("test description");
    }

    @Test
    public void test_failExecutingPost() {
        webTestClient.post().uri("/webflux-test").exchange()
                .expectStatus().isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }
    @Test
    public void empty(){
        Mono<String> emptyMono = Mono.empty();

    }

}
