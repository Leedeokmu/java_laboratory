package com.freeefly.profile.router;


import com.freeefly.profile.model.Profile;
import com.freeefly.profile.repository.ProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@WebFluxTest
public abstract class AbstractBaseProfileEndpoints {
    private final WebTestClient webTestClient;

    private final static MediaType jsonUtf8 = MediaType.APPLICATION_JSON_UTF8;

    @MockBean
    private ProfileRepository profileRepository;

    public AbstractBaseProfileEndpoints(WebTestClient webTestClient){
        this.webTestClient = webTestClient;
    }

    @Test
    public void getAll(){
        log.info("running on" + this.getClass().getName());

        Mockito
            .when(this.profileRepository.findAll())
            .thenReturn(Flux.just(new Profile("1", "A"), new Profile("2", "B")));

        this.webTestClient
                .get()
                .uri("/profiles")
                .accept(jsonUtf8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(jsonUtf8)
                .expectBody()
                .jsonPath("$.[0].id").isEqualTo("1")
                .jsonPath("$.[0].email").isEqualTo("A")
                .jsonPath("$.[1].id").isEqualTo("2")
                .jsonPath("$.[1].email").isEqualTo("B");
    }

    @Test
    public void save(){
        Profile data = new Profile("123", UUID.randomUUID().toString() + "@email.com");
        Mockito
                .when(this.profileRepository.save(Mockito.any(Profile.class)))
                .thenReturn(Mono.just(data));

        this.webTestClient
                .post()
                .uri("/profiles")
                .contentType(jsonUtf8)
                .body(Mono.just(data), Profile.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(jsonUtf8);
    }

    @Test
    public void delete(){
        Profile data = new Profile("123", UUID.randomUUID().toString() + "@email.com");
        Mockito
                .when(this.profileRepository.findById(data.getId()))
                .thenReturn(Mono.just(data));

        Mockito
                .when(this.profileRepository.deleteById(data.getId()))
                .thenReturn(Mono.empty());

        this.webTestClient
                .delete()
                .uri("/profiles/" + data.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public  void update(){
        Profile data = new Profile("123", UUID.randomUUID().toString() + "@email.com");
        Mockito
                .when(this.profileRepository.findById(data.getId()))
                .thenReturn(Mono.just(data));

        Mockito
                .when(this.profileRepository.save(data))
                .thenReturn(Mono.just(data));

        this.webTestClient
                .put()
                .uri("/profiles/" + data.getId())
                .contentType(jsonUtf8)
                .body(Mono.just(data), Profile.class)
                .exchange()
                .expectStatus().isOk();
    }

}
