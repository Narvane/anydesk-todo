package com.anydesk.domain.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Long persistentId) {
        super("Task with id " + persistentId + " was not found");
    }

}
