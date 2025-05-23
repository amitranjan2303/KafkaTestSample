package com.consumer.consumer.service;


import com.consumer.consumer.constans.AppConstant;
import com.consumer.consumer.dto.LocationInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KafkaConsumerService {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    // Example in-memory storage (replace with your actual storage)
    private final Map<String, LocationInfo> messageStore = new ConcurrentHashMap<>();

    @KafkaListener(topics = AppConstant.LOCATION_TOPIC_NAME, groupId = AppConstant.GROUP_ID)
    public void consumeMessage(String message) {

        logger.info("Received message: {}", message);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            // 1. Validate the message
            if (message == null || message.isEmpty()) {
                throw new IllegalArgumentException("Invalid message format");
            }

            LocationInfo location = mapper.readValue(message, LocationInfo.class);
            // 2. Process the message (business logic)
            processMessage(location);

            // 3. Update application state
            updateApplicationState(location);

            logger.info("Message processed successfully: {}", location.getLatitude());
        } catch (Exception e) {
            logger.error("Error processing message: {}", message, e);
            // Add to dead letter queue or error handling logic
        }
    }

    private void processMessage(LocationInfo message) {
        // Add your business logic here
        // Example: Transform data, call external services, etc.
    }

    private void updateApplicationState(LocationInfo message) {
        // Update your application state
        messageStore.put("location-info", message);

        // You might also:
        // - Update database
        // - Refresh cache
        // - Notify other components
        // - Trigger business workflows
    }

    // Optional: Getter for stored messages
    public LocationInfo getMessage(String id) {
        return messageStore.get(id);
    }
}
