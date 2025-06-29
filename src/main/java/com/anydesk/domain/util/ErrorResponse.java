package com.anydesk.domain.util;

import java.util.List;
import java.util.Locale;

public class ErrorResponse {
    public String message;
    public List<String> details;

    public ErrorResponse(String message, Locale locale, Object... params) {
        this.message = Messages.get(message, locale, params);
    }

    public ErrorResponse(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

}