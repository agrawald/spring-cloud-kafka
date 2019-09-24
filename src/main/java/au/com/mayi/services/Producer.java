package au.com.mayi.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public DeferredResult<ResponseEntity<String>> produce(String message) {
        final DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();
        kafkaTemplate.send("test", message);
        final ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test", message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
                deferredResult.setResult(ResponseEntity.ok().body(
                        "Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]"));
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[{}]", message, ex);
                deferredResult.setResult(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Unable to send message=[" + message + "] due to " + ex.getMessage()));
            }
        });
        return deferredResult;
    }
}