package au.com.mayi.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Consumer {
    @KafkaListener(topics = "test", groupId = "test-0")
    public void listen(String message) {
        log.info("Received Messasge in group foo: {}", message);
    }
}