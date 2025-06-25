package com.anydesk.domain.usecase;

import com.anydesk.domain.exception.TaskNotFoundException;
import com.anydesk.domain.repository.TaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DeleteTaskUseCase {

    @Inject
    TaskRepository taskRepository;

    public Response exec(Long persistenceId) {
        return taskRepository.findById(persistenceId).map(task -> {
            taskRepository.deleteById(task.getPersistenceId());

            return new Response(task.getPersistenceId(), task.getTitle());
        }).orElseThrow(() -> new TaskNotFoundException(persistenceId));
    }

    public record Response(Long id, String title) {}

}
