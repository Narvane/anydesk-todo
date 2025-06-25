package com.anydesk.domain.usecase;

import com.anydesk.domain.repository.TaskRepository;
import com.anydesk.domain.exception.TaskNotFoundException;
import com.anydesk.domain.model.Task;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
class DeleteTaskUseCaseTest {

    @InjectMock
    TaskRepository taskRepository;

    @Inject
    DeleteTaskUseCase useCase;

    @Test
    void shouldDeleteTaskSuccessfully() {
        Long taskId = 1L;
        Task task = mock(Task.class);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(task.getPersistenceId()).thenReturn(taskId);
        when(task.getTitle()).thenReturn("Sample task");

        DeleteTaskUseCase.Response response = useCase.exec(taskId);

        verify(taskRepository).deleteById(taskId);
        assertEquals(taskId, response.id());
        assertEquals("Sample task", response.title());
    }

    @Test
    void shouldThrowWhenTaskNotFound() {
        Long taskId = 99L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> useCase.exec(taskId));
    }
}
