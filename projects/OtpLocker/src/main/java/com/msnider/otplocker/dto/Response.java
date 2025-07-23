package com.msnider.otplocker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Response<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    
    @JsonUnwrapped
    private T value;
    
    // Constructor for success with value
    public Response(T value) {
        this.value = value;
    }
    
    // Constructor for error with message
    public Response(String message) {
        this.message = message;
    }
    
    // Constructor for success with value and message
    public Response(T value, String message) {
        this.value = value;
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getValue() {
        return value;
    }
    
    public void setValue(T value) {
        this.value = value;
    }
    
    // Static factory methods for cleaner usage
    public static <T> Response<T> success(T value) {
        return new Response<>(value);
    }
    
    public static <T> Response<T> error(String message) {
        return new Response<>(message);
    }
    
    public static <T> Response<T> success(T value, String message) {
        return new Response<>(value, message);
    }
}