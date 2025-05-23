package com.deliveryagent.deliveryagent.service;

import com.deliveryagent.deliveryagent.constans.AppConstant;
import com.deliveryagent.deliveryagent.dto.LocationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, LocationInfo> kafkaTemplate;
    private final Logger logger = LoggerFactory.getLogger(KafkaService.class);
    private static final long PRODUCE_TIMEOUT_MS = 5000; // 5-second timeout


    public CompletableFuture<SendResult<String, LocationInfo>> produceMessage(LocationInfo locationInfo) {
        validateMessage(locationInfo);

        CompletableFuture<SendResult<String, LocationInfo>> future =
                kafkaTemplate.send(AppConstant.LOCATION_TOPIC_NAME, locationInfo);

        return future.whenComplete(this::handleSendResult);
    }

    /**
     * Produces a message to Kafka with synchronous wait
     * @param message The message to send
     * @return SendResult if successful
     * @throws RuntimeException if sending fails or times out
     */
    public SendResult<String, LocationInfo> produceMessageAsync(LocationInfo message) {
        validateMessage(message);

        try {
            return produceMessage(message)
                    .get(PRODUCE_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            String error = String.format("Message production timed out after %dms", PRODUCE_TIMEOUT_MS);
            logger.error(error, e);
            throw new RuntimeException(error, e);
        } catch (Exception e) {
            logger.error("Failed to send message: {}", message, e);
            throw new RuntimeException("Failed to send message to Kafka", e);
        }
    }

    private void validateMessage(LocationInfo message) {
        if (message == null) {
            logger.warn("Attempted to send null or empty message");
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
    }

    private void handleSendResult(SendResult<String, LocationInfo> result, Throwable ex) {
        if (ex != null) {
            logger.error("Failed to send Kafka message", ex);
        } else {
            logger.info("Message sent successfully: topic={}, partition={}, offset={}",
                    result.getRecordMetadata().topic(),
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
        }
    }
}
