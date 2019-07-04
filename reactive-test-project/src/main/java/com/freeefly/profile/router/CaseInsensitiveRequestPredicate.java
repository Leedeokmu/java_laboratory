package com.freeefly.profile.router;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.ServerRequest;

@RequiredArgsConstructor
@org.springframework.context.annotation.Profile("default")
public class CaseInsensitiveRequestPredicate implements RequestPredicate {
    private final RequestPredicate target;

    @Override
    public String toString() {
        return this.target.toString();
    }

    @Override
    public boolean test(ServerRequest request) {
        return this.target.test(new LowerCaseUriServerRequestWrapper(request));
    }
}
