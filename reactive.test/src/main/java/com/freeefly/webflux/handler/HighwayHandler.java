package com.freeefly.webflux.handler;

import com.freeefly.webflux.Service.HighwayTraffic;
import com.freeefly.webflux.model.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class HighwayHandler {
    private final HighwayTraffic highwayTraffic;

    public Mono<ServerResponse> vehicleDetected(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(highwayTraffic.flowTraffic(), Vehicle.class);
    }


}
