package com.example.weatherrestapi.model;


import lombok.Data;

@Data
public class ServiceResponse<T> {
    private T data;
    private boolean success;
    private String message;

    public ServiceResponse() {
    }

    public ServiceResponse(T data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }
}
