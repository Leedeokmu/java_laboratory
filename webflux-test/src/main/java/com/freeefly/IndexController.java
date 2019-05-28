package com.freeefly;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@RestController
public class IndexController {
    private final ForkJoinPool forkJoinPool;

    @GetMapping("/process-blocking")
    public ResponseEntity<?> handleReqSync() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("async-deferredresult")
    public DeferredResult<ResponseEntity<?>> handleReqDefResult(Model model){
        log.info("Received async-deferredresult request");
        DeferredResult<ResponseEntity<?>> deferredResult = new DeferredResult<>(1000L);
        deferredResult.onTimeout(() ->
            deferredResult.setErrorResult(
                ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).
                        body("Request timeout occurred!")
            )
        );

        forkJoinPool.submit(() -> {
            log.info("Processing in separate thread");
            try {
                TimeUnit.MILLISECONDS.sleep(3000L);
            } catch (InterruptedException e) { }

            deferredResult.setResult(ResponseEntity.ok("ok"));
        });
        log.info("servlet thread freed");
        return deferredResult;
    }


}
