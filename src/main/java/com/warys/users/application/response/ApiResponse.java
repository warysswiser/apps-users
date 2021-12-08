package com.warys.users.application.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse<T> extends ResponseEntity<T> {

    public ApiResponse(T body, HttpStatus status) {
        super(body, status);
    }

    public ApiResponse(HttpStatus status) {
        super(status);
    }

    public static <T> ApiResponse<T> created(T body) {
        return new ApiResponse<>(body, HttpStatus.CREATED);
    }

    public static <T> ApiResponse<T> ok(T body) {
        return new ApiResponse<>(body, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> deleted() {
        return new ApiResponse<>(HttpStatus.NO_CONTENT);
    }
}
