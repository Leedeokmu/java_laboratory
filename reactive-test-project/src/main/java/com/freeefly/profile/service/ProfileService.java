package com.freeefly.profile.service;

import com.freeefly.profile.event.ProfileCreatedEvent;
import com.freeefly.profile.model.Profile;
import com.freeefly.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProfileService {
    private final ApplicationEventPublisher publisher;
    private final ProfileRepository profileRepository;

    public Flux<Profile> all() {
        return this.profileRepository.findAll();
    }

    public Mono<Profile> get(String id){
        return this.profileRepository.findById(id);
    }

    public Mono<Profile> update(String id, String email){
        return this.profileRepository
                .findById(id)
                .map(profile -> new Profile(profile.getId(), email))
                .flatMap(profileRepository::save);
    }

    public Mono<Profile> delete(String id) {
        return this.profileRepository
                .findById(id)
                .flatMap(p -> this.profileRepository.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Profile> create(String email){
        return this.profileRepository
                .save(new Profile(null, email))
                .doOnSuccess(profile -> this.publisher.publishEvent(new ProfileCreatedEvent(profile)));
    }


}
