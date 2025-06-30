package com.anydesk.app.resource.task;

import com.anydesk.domain.usecase.task.*;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static com.anydesk.domain.model.TaskStatus.COMPLETED;

@Path("/tasks")
@RolesAllowed("USER")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

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
    public Response listAll(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize) {
        var tasks =  listTasksUseCase.execute(page, pageSize);

        if (tasks.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.OK).entity(tasks).build();
    }

    @GET
    @Path("/completed")
    public Response listCompleted(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize) {
        var tasks = listTasksUseCase.execute(COMPLETED, page, pageSize);

        if (tasks.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.OK).entity(tasks).build();
    }

    @POST
    public Response create(@Valid CreateTaskUseCase.Request request) {
        var task = createTaskUseCase.exec(request);
        return Response.status(Response.Status.CREATED).entity(task).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid UpdateTaskUseCase.Request request) {
        var task = updateTaskUseCase.exec(id, request);
        return Response.status(Response.Status.OK).entity(task).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        var task = deleteTaskUseCase.exec(id);
        return Response.status(Response.Status.OK).entity(task).build();
    }

    @GET
    @Path("/{id}/toggle-status")
    public Response toggleStatus(@PathParam("id") Long id) {
        var task = toggleTaskStatusUseCase.exec(id);
        return Response.status(Response.Status.OK).entity(task).build();
    }

}
