package com.anydesk.domain.usecase.task;

import com.anydesk.domain.exception.TaskNotFoundException;
import com.anydesk.domain.model.Task;
import com.anydesk.domain.model.TaskStatus;
import com.anydesk.domain.repository.TaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ToggleTaskStatusUseCase {

    @Inject
    TaskRepository taskRepository;

    public Response exec(Long persistenceId) {
        return taskRepository.findById(persistenceId).map(task -> {
            task.toggleStatus();

            Task savedTask = taskRepository.save(task);
            return new Response(savedTask.getPersistenceId(), savedTask.getStatus());
        }).orElseThrow(() -> new TaskNotFoundException(persistenceId));
    }

    public record Response(Long id, TaskStatus status) {}

}
