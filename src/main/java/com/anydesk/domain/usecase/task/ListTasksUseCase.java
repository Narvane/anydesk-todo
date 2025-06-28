package com.anydesk.domain.usecase.task;

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
                .map(task -> new Response(
                        task.getPersistenceId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus())
                );
    }

    public Page<Response> execute(TaskStatus taskStatus, int page, int size) {
        return taskRepository.findAll(taskStatus, page, size)
                .map(task -> new Response(
                        task.getPersistenceId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus())
                );
    }

    public record Response(Long id, String title, String description, TaskStatus status) {}

}
