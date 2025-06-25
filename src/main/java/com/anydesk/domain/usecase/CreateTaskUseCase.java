package com.anydesk.domain.usecase;

import com.anydesk.domain.model.Task;
import com.anydesk.domain.model.TaskStatus;
import com.anydesk.domain.repository.TaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
                savedTask.getStatus().getText()
        );
    }

    public record Request(String title, String description, TaskStatus status) {}
    public record Response(Long id, String title, String description, String status) {}

}
