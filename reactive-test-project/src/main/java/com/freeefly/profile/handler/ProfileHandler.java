package com.freeefly.profile.handler;


import com.freeefly.profile.model.Profile;
import com.freeefly.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
@org.springframework.context.annotation.Profile("default")
public class ProfileHandler {
    private final ProfileService profileService;

    public Mono<ServerResponse> getById(ServerRequest request){
        String id = request.pathVariable("id");
        return this.defaultReadResponse(profileService.get(id));
    }

    public Mono<ServerResponse> all(ServerRequest request){
        return this.defaultReadResponse(profileService.all());
    }

    public Mono<ServerResponse> deleteById(ServerRequest request){
        String id = request.pathVariable("id");
        return this.defaultReadResponse(profileService.delete(id));
    }

    public Mono<ServerResponse> updateById(ServerRequest request){
        String id = request.pathVariable("id");
        Flux<Profile> flux = request
                .bodyToFlux(Profile.class)
                .flatMap(profile -> this.profileService.update(id, profile.getEmail()));
        return this.defaultReadResponse(flux);
    }

    public Mono<ServerResponse> create(ServerRequest request){
        Flux<Profile> flux = request
                .bodyToFlux(Profile.class)
                .flatMap(toWrite -> this.profileService.create(toWrite.getEmail()));
        return this.defaultWriteResponse(flux);
    }

    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Profile> profiles) {
        return Mono
                .from(profiles)
                .flatMap(profile -> ServerResponse.
                        created(URI.create("/profiles/" + profile.getId()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .build()
                );
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<Profile> profiles){
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(profiles, Profile.class);
    }

}
