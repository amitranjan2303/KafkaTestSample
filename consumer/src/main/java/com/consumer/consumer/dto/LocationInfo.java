package com.consumer.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
public class LocationInfo {
    private String deviceId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
}
