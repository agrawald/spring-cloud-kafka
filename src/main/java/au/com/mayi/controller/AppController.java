package au.com.mayi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import au.com.mayi.services.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AppController {
    private final Producer producer;

    @PutMapping(path = "/send")
    public DeferredResult<ResponseEntity<String>> sendMessage(@RequestBody String message) {
        log.info("Received: {}", message);
        return producer.produce(message);
    }
}