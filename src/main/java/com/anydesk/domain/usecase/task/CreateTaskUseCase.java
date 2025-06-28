package com.anydesk.domain.usecase.task;

import com.anydesk.domain.model.Task;
import com.anydesk.domain.model.TaskStatus;
import com.anydesk.domain.repository.TaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@ApplicationScoped
public class CreateTaskUseCase {

    @Inject
    TaskRepository taskRepository;

    public Response exec(Request request) {
        Task task = new Task(request.title, request.description, request.status);

        Task savedTask = taskRepository.save(task);
        return new Response(
                savedTask.getPersistenceId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus()
        );
    }

    public record Request(
            @NotBlank(message = "{validation.title.not.blank}")
            @Size(max = 255, message = "{validation.title.size}")
            String title,

            @Size(max = 500, message = "{validation.description.size}")
            String description,

            TaskStatus status
    ) {}
    public record Response(Long id, String title, String description, TaskStatus status) {}

}
