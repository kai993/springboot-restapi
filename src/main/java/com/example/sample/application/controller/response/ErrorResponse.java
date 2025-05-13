package com.example.sample.application.controller.response;

import lombok.Data;

@Data
public class ErrorResponse {
    private String logId;
    private String message;
    private String detail;

    public ErrorResponse(String message, String logId, String detail) {
        this.logId = logId;
        this.message = message;
        this.detail = detail;
    }
}

