package com.anydesk.domain.usecase.task;

import com.anydesk.domain.exception.TaskNotFoundException;
import com.anydesk.domain.model.Task;
import com.anydesk.domain.repository.TaskRepository;
import com.anydesk.domain.usecase.task.ToggleTaskStatusUseCase.Response;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.anydesk.domain.model.TaskStatus.COMPLETED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
class ToggleTaskStatusUseCaseTest {

    @InjectMock
    TaskRepository taskRepository;

    @Inject
    ToggleTaskStatusUseCase useCase;

    @Test
    void shouldToggleTaskStatusSuccessfully() {
        Long taskId = 1L;
        Task task = mock(Task.class);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(task.getPersistenceId()).thenReturn(taskId);
        when(task.getStatus()).thenReturn(COMPLETED);

        Response response = useCase.exec(taskId);

        verify(task).toggleStatus();
        verify(taskRepository).save(task);
        assertEquals(taskId, response.id());
        assertEquals(COMPLETED, response.status());
    }

    @Test
    void shouldThrowWhenTaskNotFound() {
        Long taskId = 99L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> useCase.exec(taskId));
    }
}
