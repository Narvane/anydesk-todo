package com.anydesk.domain.model;

import static com.anydesk.domain.model.TaskStatus.*;

public class Task {
    private Long persistenceId;
    private String title;
    private String description;
    private TaskStatus status = TODO;

    public Task() {}

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Task(String title, String description, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.status = status != null ? status : TODO;
    }

    public Task(Long persistenceId, String title, String description, TaskStatus status) {
        this.persistenceId = persistenceId;
        this.title = title;
        this.description = description;
        this.status = status != null ? status : TODO;
    }

    public void toggleStatus() {
        if (status == TODO) {
            this.status = COMPLETED;
        } else {
            this.status = TODO;
        }
    }

    public Long getPersistenceId() {
        return persistenceId;
    }

    public void setPersistenceId(Long persistenceId) {
        this.persistenceId = persistenceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

}
