package com.anydesk.domain.usecase.task;

import com.anydesk.domain.exception.TaskNotFoundException;
import com.anydesk.domain.model.Task;
import com.anydesk.domain.model.TaskStatus;
import com.anydesk.domain.repository.TaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@ApplicationScoped
public class UpdateTaskUseCase {

    @Inject
    TaskRepository taskRepository;

    public Response exec(Long id, Request taskUpdate) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskUpdate.title);
            task.setDescription(taskUpdate.description);
            task.setStatus(taskUpdate.status);

            Task updatedTask = taskRepository.save(task);
            return new Response(
                    updatedTask.getPersistenceId(),
                    updatedTask.getTitle(),
                    updatedTask.getDescription(),
                    updatedTask.getStatus()
            );
        }).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public record Request(
            @NotBlank(message = "{validation.title.not.blank}")
            @Size(max = 255, message = "{validation.title.size}")
            String title,

            @Size(max = 500, message = "{validation.description.size}")
            String description,

            @NotNull(message = "{update.request.status.notnull}") TaskStatus status
    ) {}
    public record Response(Long id, String title, String description, TaskStatus status) {}

}
