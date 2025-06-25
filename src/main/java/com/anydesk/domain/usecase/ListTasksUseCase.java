package com.anydesk.domain.usecase;

import com.anydesk.domain.model.TaskStatus;
import com.anydesk.domain.repository.TaskRepository;
import com.anydesk.domain.util.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ListTasksUseCase {

    @Inject
    TaskRepository taskRepository;

    public Page<Response> execute(int page, int size) {
        return taskRepository.findAll(page, size)
                .map(task -> new Response(task.getTitle(), task.getDescription(), task.getStatus().getText()));
    }

    public Page<Response> execute(TaskStatus taskStatus, int page, int size) {
        return taskRepository.findAll(page, size)
                .map(task -> new Response(task.getTitle(), task.getDescription(), task.getStatus().getText()));
    }

    public record Response(String title, String description, String status) {}

}
