package com.freeefly.profile.router;

import com.freeefly.profile.controller.ProfileRestController;
import com.freeefly.profile.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@Slf4j
@Import({ProfileRestController.class, ProfileService.class})
@ActiveProfiles("classic")
public class ClassicProfileEndpointsTests extends AbstractBaseProfileEndpoints{

    @BeforeAll
    static void before(){
        log.info("running classic test");
    }
    ClassicProfileEndpointsTests(@Autowired WebTestClient client) {
        super(client);
    }

}
