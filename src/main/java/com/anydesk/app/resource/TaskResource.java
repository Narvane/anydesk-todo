package com.anydesk.app.resource;

import com.anydesk.app.resource.doc.TaskResourceDoc;
import com.anydesk.domain.usecase.*;
import com.anydesk.domain.util.Page;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import static com.anydesk.domain.model.TaskStatus.COMPLETED;

@Path("/tasks")
public class TaskResource implements TaskResourceDoc {

    @Inject
    ListTasksUseCase listTasksUseCase;
    @Inject
    CreateTaskUseCase createTaskUseCase;
    @Inject
    UpdateTaskUseCase updateTaskUseCase;
    @Inject
    DeleteTaskUseCase deleteTaskUseCase;
    @Inject
    ToggleTaskStatusUseCase toggleTaskStatusUseCase;

    @GET
    public Page<ListTasksUseCase.Response> listAll(int page, int pageSize) {
        return listTasksUseCase.execute(page, pageSize);
    }

    @GET
    @Path("/completed")
    public Page<ListTasksUseCase.Response> listCompleted(int page, int pageSize) {
        return listTasksUseCase.execute(COMPLETED, page, pageSize);
    }

    @POST
    public CreateTaskUseCase.Response create(CreateTaskUseCase.Request request) {
        return createTaskUseCase.exec(request);
    }

    @PUT
    @Path("/{id}")
    public UpdateTaskUseCase.Response update(@PathParam("id") Long id, UpdateTaskUseCase.Request request) {
        return updateTaskUseCase.exec(id, request);
    }

    @DELETE
    @Path("/{id}")
    public DeleteTaskUseCase.Response delete(@PathParam("id") Long id) {
        return deleteTaskUseCase.exec(id);
    }

    @GET
    @Path("/{id}/toggle-status")
    public ToggleTaskStatusUseCase.Response toggleStatus(@PathParam("id") Long id) {
        return toggleTaskStatusUseCase.exec(id);
    }

}
