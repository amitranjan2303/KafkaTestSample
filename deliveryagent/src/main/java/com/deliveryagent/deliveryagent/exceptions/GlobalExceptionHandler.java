package com.deliveryagent.deliveryagent.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.deliveryagent.deliveryagent.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("An error occurred")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(org.springframework.kafka.KafkaException.class)
    public ResponseEntity<ErrorResponse> handleKafkaExceptions(org.springframework.kafka.KafkaException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Kafka communication error")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
//        ErrorResponse errorResponse = ErrorResponse.builder()
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .error("Error occurred")
//                .message(ex.getMessage())
//                .build();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(errorResponse);
//    }
}

