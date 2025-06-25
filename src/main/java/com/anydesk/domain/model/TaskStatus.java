package com.anydesk.domain.model;

public enum TaskStatus {
    TODO("To do"),
    COMPLETED("Completed");

    private final String text;

    TaskStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
