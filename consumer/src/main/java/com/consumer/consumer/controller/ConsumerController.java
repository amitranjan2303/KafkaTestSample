package com.consumer.consumer.controller;

import com.consumer.consumer.dto.LocationInfo;
import com.consumer.consumer.service.KafkaConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class ConsumerController {

    @Autowired
    private KafkaConsumerService kafkaConsumerService;

    @GetMapping("/{id}")
    public ResponseEntity<LocationInfo> getMessage(@PathVariable String id) {
        LocationInfo message = kafkaConsumerService.getMessage(id);
        if (message != null) {
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.notFound().build();
    }
}
