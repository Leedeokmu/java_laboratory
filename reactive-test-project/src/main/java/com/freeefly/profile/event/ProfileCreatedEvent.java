package com.freeefly.profile.event;

import com.freeefly.profile.model.Profile;
import org.springframework.context.ApplicationEvent;

public class ProfileCreatedEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param profile the object on which the event initially occurred (never {@code null})
     */
    public ProfileCreatedEvent(Profile profile) {
        super(profile);
    }
}
