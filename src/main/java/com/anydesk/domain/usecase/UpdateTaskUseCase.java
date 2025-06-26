package com.anydesk.domain.usecase;

import com.anydesk.domain.exception.TaskNotFoundException;
import com.anydesk.domain.model.Task;
import com.anydesk.domain.repository.TaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UpdateTaskUseCase {

    @Inject
    TaskRepository taskRepository;

    public Response exec(Long id, Request taskUpdate) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskUpdate.title);
            task.setDescription(taskUpdate.description);

            Task updatedTask = taskRepository.save(task);
            return new Response(
                    updatedTask.getPersistenceId(),
                    updatedTask.getTitle(),
                    updatedTask.getDescription(),
                    updatedTask.getStatus().getText()
            );
        }).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public record Request(String title, String description) {}
    public record Response(Long id, String title, String description, String status) {}

}
