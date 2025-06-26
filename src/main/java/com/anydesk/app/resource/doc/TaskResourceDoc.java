package com.anydesk.app.resource.doc;

import com.anydesk.domain.usecase.*;
import com.anydesk.domain.util.Page;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface TaskResourceDoc {

    @GET
    @Operation(summary = "List all tasks", description = "Returns a paginated list of all tasks.")
    @APIResponse(responseCode = "200", description = "List of tasks")
    Page<ListTasksUseCase.Response> listAll(
            @Parameter(description = "Page number") @QueryParam("page") int page,
            @Parameter(description = "Page size") @QueryParam("pageSize") int pageSize
    );

    @GET
    @Path("/completed")
    @Operation(summary = "List completed tasks", description = "Returns a paginated list of completed tasks.")
    @APIResponse(responseCode = "200", description = "List of completed tasks")
    Page<ListTasksUseCase.Response> listCompleted(
            @Parameter(description = "Page number") @QueryParam("page") int page,
            @Parameter(description = "Page size") @QueryParam("pageSize") int pageSize
    );

    @POST
    @Operation(summary = "Create a new task", description = "Creates a new task with the given data.")
    @APIResponse(responseCode = "201", description = "Task created")
    CreateTaskUseCase.Response create(
            @RequestBody(content = @Content(schema = @Schema(implementation = CreateTaskUseCase.Request.class)))
            CreateTaskUseCase.Request request
    );

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a task", description = "Updates an existing task.")
    @APIResponse(responseCode = "200", description = "Task updated")
    UpdateTaskUseCase.Response update(
            @Parameter(description = "Task ID") @PathParam("id") Long id,
            @RequestBody(content = @Content(schema = @Schema(implementation = UpdateTaskUseCase.Request.class)))
            UpdateTaskUseCase.Request request
    );

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a task", description = "Deletes a task by ID.")
    @APIResponse(responseCode = "204", description = "Task deleted")
    DeleteTaskUseCase.Response delete(
            @Parameter(description = "Task ID") @PathParam("id") Long id
    );

    @GET
    @Path("/{id}/toggle-status")
    @Operation(summary = "Toggle task status", description = "Toggles a task's status.")
    @APIResponse(responseCode = "200", description = "Task status toggled")
    ToggleTaskStatusUseCase.Response toggleStatus(
            @Parameter(description = "Task ID") @PathParam("id") Long id
    );

}