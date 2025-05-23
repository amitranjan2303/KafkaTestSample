package com.deliveryagent.deliveryagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ApiResponse {
    private String status;
    private String timestamp;
    private String message;
}
