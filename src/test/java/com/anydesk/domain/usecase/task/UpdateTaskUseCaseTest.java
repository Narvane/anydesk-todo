package com.anydesk.domain.usecase.task;

import com.anydesk.domain.exception.TaskNotFoundException;
import com.anydesk.domain.model.Task;
import com.anydesk.domain.repository.TaskRepository;
import com.anydesk.domain.usecase.task.UpdateTaskUseCase.Request;
import com.anydesk.domain.usecase.task.UpdateTaskUseCase.Response;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.anydesk.domain.model.TaskStatus.COMPLETED;
import static com.anydesk.domain.model.TaskStatus.TODO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@QuarkusTest
class UpdateTaskUseCaseTest {

    @InjectMock
    TaskRepository taskRepository;

    @Inject
    UpdateTaskUseCase useCase;

    @Test
    void shouldUpdateTaskSuccessfully() {
        Long taskId = 1L;
        Request request = new Request("New title", "New desc", TODO);
        Task task = mock(Task.class);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        when(task.getPersistenceId()).thenReturn(taskId);
        when(task.getTitle()).thenReturn("New title");
        when(task.getDescription()).thenReturn("New desc");
        when(task.getStatus()).thenReturn(TODO);

        Response response = useCase.exec(taskId, request);

        verify(task).setTitle("New title");
        verify(task).setDescription("New desc");
        verify(taskRepository).save(task);

        assertEquals(taskId, response.id());
        assertEquals("New title", response.title());
        assertEquals("New desc", response.description());
        assertEquals(TODO, response.status());
    }

    @Test
    void shouldThrowWhenTaskNotFound() {
        Long taskId = 99L;
        Request request = new Request("Title", "Desc", TODO);

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> useCase.exec(taskId, request));
    }

}
