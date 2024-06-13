package com.supamenu.www.exceptions;

import com.supamenu.www.dtos.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public ResponseEntity<ApiResponse<Object>> getResponse() {
        return ApiResponse.error("Failed to get a resource", HttpStatus.NOT_FOUND, null);
    }
}
