package com.freeefly;

import com.freeefly.profile.model.Profile;
import com.freeefly.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@org.springframework.context.annotation.Profile("test")
public class InitDatabase implements ApplicationListener<ApplicationReadyEvent> {
    private final ProfileRepository profileRepository;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        profileRepository
                .deleteAll()
                .thenMany(
                        Flux.just("A", "B", "C", "D")
                                .map(name -> new Profile(UUID.randomUUID().toString(), name + "@email.com"))
                                .flatMap(profileRepository::save)
                )
                .thenMany(profileRepository.findAll())
                .subscribe(profile -> log.info("saving " + profile.toString()));
    }
}
