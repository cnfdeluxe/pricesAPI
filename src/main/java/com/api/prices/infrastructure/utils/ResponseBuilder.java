package com.api.prices.infrastructure.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    public static <T> ResponseEntity<ApiResponse<T>> with(HttpStatus status, boolean success, T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(status.value(), success, data, message);
        return new ResponseEntity<>(response, status);
    }
}
