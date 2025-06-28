package com.anydesk.app.vo;

import java.util.List;

public class ErrorResponse {
    public String message;
    public List<String> details;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }
}