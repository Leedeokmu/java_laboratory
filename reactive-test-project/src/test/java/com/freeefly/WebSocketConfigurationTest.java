package com.freeefly;

import com.freeefly.profile.model.Profile;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebSocketConfigurationTest {

    private final WebSocketClient webSocketClient = new ReactorNettyWebSocketClient();
    private final WebClient webClient = WebClient.builder().build();

    private Profile generatedRandomProfile() {
        return new Profile(UUID.randomUUID().toString(), UUID.randomUUID().toString() + "@email.com");
    }

    @Test
    public void testNotificationOnUpdate() throws  Exception{
        int count = 10;
        AtomicLong counter = new AtomicLong();
        URI uri = URI.create("ws://localhost:8000/ws/profiles");

        webSocketClient.execute(uri, (session) -> {
            Mono<WebSocketMessage> out = Mono.just(session.textMessage("test"));
            Flux<String> in = session
                    .receive()
                    .map(WebSocketMessage::getPayloadAsText);

            return session
                    .send(out)
                    .thenMany(in)
                    .doOnNext(str -> counter.incrementAndGet())
                    .then();
        }).subscribe();

        Flux.<Profile>generate(sink -> sink.next(generatedRandomProfile()))
                .take(count)
                .flatMap(this::write)
                .blockLast();

        TimeUnit.MILLISECONDS.sleep(1000);
        Assertions.assertThat(counter.get()).isEqualTo(count);

    }

    private Publisher<Profile> write(Profile profile){
        return
                this.webClient
                        .post()
                        .uri("http://localhost:8000/profiles")
                        .body(BodyInserters.fromObject(profile))
                        .retrieve()
                        .bodyToMono(String.class)
                        .thenReturn(profile);
    }
}
