package com.anydesk.domain.exception;

public class TaskNotFoundException extends RuntimeException {

    private final Object[] params;

    public TaskNotFoundException(Long persistentId) {
        super("exception.task.notfound");
        this.params = new Object[]{persistentId};
    }

    public Object[] getParams() {
        return params;
    }

}
