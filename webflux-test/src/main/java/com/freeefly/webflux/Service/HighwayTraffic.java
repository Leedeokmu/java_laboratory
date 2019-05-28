package com.freeefly.webflux.Service;

import com.freeefly.webflux.utils.HighwayUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class HighwayTraffic {

    public Flux flowTraffic() {
        LocalDateTime startTime = LocalDateTime.now();
        return Flux.create(fluxSink -> {

            boolean inFrameTime = true;
            int index = 1;
            while (index <= 30000 && inFrameTime && !fluxSink.isCancelled() ) {
                fluxSink.next(HighwayUtilities.simulateTraffic());
                index++;
                long timeMinutesHighwayOpened = startTime.until(LocalDateTime.now(),
                        ChronoUnit.MILLIS);
                if (timeMinutesHighwayOpened > 30000) {
                    log.info("TrafficSimulator finish --> With timer");
                    inFrameTime = false;
                }
            }
        }).share();
    }

}
