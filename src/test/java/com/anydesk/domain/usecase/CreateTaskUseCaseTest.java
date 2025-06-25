package com.anydesk.domain.usecase;

import com.anydesk.domain.repository.TaskRepository;
import com.anydesk.domain.model.Task;
import com.anydesk.domain.model.TaskStatus;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static com.anydesk.domain.usecase.CreateTaskUseCase.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@QuarkusTest
class CreateTaskUseCaseTest {

    @InjectMock
    TaskRepository taskRepository;

    @Inject
    CreateTaskUseCase useCase;

    @Test
    void shouldCreateTaskSuccessfully() {
        Request request = new Request("Title", "Description", TaskStatus.COMPLETED);
        Task mockTask = mock(Task.class);

        when(taskRepository.save(any(Task.class))).thenReturn(mockTask);
        when(mockTask.getPersistenceId()).thenReturn(1L);
        when(mockTask.getTitle()).thenReturn("Title");
        when(mockTask.getDescription()).thenReturn("Description");
        when(mockTask.getStatus()).thenReturn(TaskStatus.COMPLETED);

        Response response = useCase.exec(request);

        assertEquals(1L, response.id());
        assertEquals("Title", response.title());
        assertEquals("Description", response.description());
        assertEquals("Completed", response.status());

        verify(taskRepository).save(any(Task.class));
    }

}
