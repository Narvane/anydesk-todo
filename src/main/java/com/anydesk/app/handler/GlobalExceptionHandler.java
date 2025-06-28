package com.anydesk.app.handler;

import com.anydesk.app.vo.ErrorResponse;
import com.anydesk.domain.exception.TaskNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

@Provider
public class GlobalExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class);

    @Provider
    public static class TaskNotFoundHandler implements ExceptionMapper<TaskNotFoundException> {
        @Override
        public Response toResponse(TaskNotFoundException ex) {
            LOGGER.warn("Task not found: " + ex.getMessage(), ex);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(ex.getMessage()))
                    .build();
        }
    }

    @Provider
    public static class ConstraintViolationHandler implements ExceptionMapper<ConstraintViolationException> {
        @Override
        public Response toResponse(ConstraintViolationException ex) {
            var details = ex.getConstraintViolations().stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.toList());

            LOGGER.debug("Validation error: " + details, ex);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Validation error", details))
                    .build();
        }
    }

    @Provider
    public static class GenericExceptionHandler implements ExceptionMapper<Exception> {
        @Override
        public Response toResponse(Exception ex) {
            LOGGER.error("Unexpected error", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Internal Server Error", List.of(ex.getMessage())))
                    .build();
        }
    }
}
