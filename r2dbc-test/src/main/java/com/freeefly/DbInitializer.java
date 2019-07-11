package com.freeefly;

import com.freeefly.model.Person;
import com.freeefly.model.Users;
import com.freeefly.repositories.PersonRepository;
import com.freeefly.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile("test")
public class DbInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final PersonRepository personRepository;
    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event){
        personRepository.deleteAll().subscribe();
//        sleep(2);

        personRepository.saveAll(
                Arrays.asList(
                        new Person("Dan Newton", 25),
                        new Person("Laura So", 23)
                )
        ).log().subscribe();

        sleep(1);
        personRepository.findAll().subscribe(person -> log.info("findAll : {}", person));



        userRepository.deleteAll().subscribe();
//        sleep(2);

        userRepository.saveAll(
                Arrays.asList(
                        new Users("Dan Newton@email.com", "test"),
                        new Users("Laura So@email.com", "test")
                )
        ).log().subscribe();

        sleep(1);
        userRepository.findAll().subscribe(person -> log.info("findAll : {}", person));



    }

    private void sleep(Integer second){
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) { }

    }
}

