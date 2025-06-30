package com.anydesk.app.resource.task.request;

import com.anydesk.domain.usecase.task.CreateTaskUseCase;
import com.anydesk.domain.usecase.task.UpdateTaskUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.anydesk.domain.model.TaskStatus.TODO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestValidationTest {

    static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    // --- CreateTaskUseCase.Request Tests ---

    @Test
    void shouldFailWhenCreateTitleIsBlank() {
        var req = new CreateTaskUseCase.Request("   ", "Some description", TODO);

        Set<ConstraintViolation<CreateTaskUseCase.Request>> violations = validator.validate(req);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void shouldFailWhenCreateTitleExceedsMaxLength() {
        var longTitle = "a".repeat(256);
        var req = new CreateTaskUseCase.Request(longTitle, "Some description", TODO);

        Set<ConstraintViolation<CreateTaskUseCase.Request>> violations = validator.validate(req);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void shouldPassWhenCreateFieldsAreValid() {
        var req = new CreateTaskUseCase.Request("Valid title", "Valid description", TODO);

        Set<ConstraintViolation<CreateTaskUseCase.Request>> violations = validator.validate(req);

        assertTrue(violations.isEmpty());
    }

    // --- UpdateTaskUseCase.Request Tests ---

    @Test
    void shouldFailWhenUpdateTitleIsBlank() {
        var req = new UpdateTaskUseCase.Request("   ", "Some description", TODO);

        Set<ConstraintViolation<UpdateTaskUseCase.Request>> violations = validator.validate(req);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void shouldFailWhenUpdateTitleExceedsMaxLength() {
        var longTitle = "a".repeat(256);
        var req = new UpdateTaskUseCase.Request(longTitle, "Some description", TODO);

        Set<ConstraintViolation<UpdateTaskUseCase.Request>> violations = validator.validate(req);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void shouldFailWhenUpdateStatusIsNull() {
        var req = new UpdateTaskUseCase.Request("Valid title", "Valid description", null);

        Set<ConstraintViolation<UpdateTaskUseCase.Request>> violations = validator.validate(req);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("status")));
    }

    @Test
    void shouldPassWhenUpdateFieldsAreValid() {
        var req = new UpdateTaskUseCase.Request("Valid title", "Valid description", TODO);

        Set<ConstraintViolation<UpdateTaskUseCase.Request>> violations = validator.validate(req);

        assertTrue(violations.isEmpty());
    }

}
