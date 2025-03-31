package com.api.prices.infrastructure.utils;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private int status;
    private boolean success;
    private T data;
    private String message;

    // Constructor, getters y setters
    public ApiResponse(int status, boolean success, T data, String message) {
        this.status = status;
        this.success = success;
        this.data = data;
        this.message = message;
    }

}
