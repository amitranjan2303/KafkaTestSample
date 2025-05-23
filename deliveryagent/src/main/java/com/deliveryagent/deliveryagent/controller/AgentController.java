package com.deliveryagent.deliveryagent.controller;

import com.deliveryagent.deliveryagent.dto.ApiResponse;
import com.deliveryagent.deliveryagent.dto.LocationInfo;
import com.deliveryagent.deliveryagent.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/agent")
public class AgentController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/publish")
    public ResponseEntity<ApiResponse> produceMessage(@RequestBody LocationInfo locationInfo) {
//        String message= locationInfo.getLatitude()+","+locationInfo.getLongitude();
        kafkaService.produceMessage(locationInfo);
        ApiResponse response = ApiResponse.builder()
                .status(String.valueOf(HttpStatus.OK))
                .timestamp(String.valueOf(LocalDateTime.now()))
                .message("Message published successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
