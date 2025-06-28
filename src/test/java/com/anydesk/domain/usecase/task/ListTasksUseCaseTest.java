package com.anydesk.domain.usecase.task;

import com.anydesk.domain.model.Task;
import com.anydesk.domain.model.TaskStatus;
import com.anydesk.domain.repository.TaskRepository;
import com.anydesk.domain.usecase.task.ListTasksUseCase.Response;
import com.anydesk.domain.util.Page;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.anydesk.domain.model.TaskStatus.COMPLETED;
import static com.anydesk.domain.model.TaskStatus.TODO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@QuarkusTest
class ListTasksUseCaseTest {

    @InjectMock
    TaskRepository taskRepository;

    @Inject
    ListTasksUseCase useCase;

    @Test
    void shouldListTasksSuccessfully() {
        int page = 0;
        int size = 2;
        long totalElements = 2;

        Task task1 = mock(Task.class);
        Task task2 = mock(Task.class);

        when(task1.getTitle()).thenReturn("Task 1");
        when(task1.getDescription()).thenReturn("Desc 1");
        when(task1.getStatus()).thenReturn(TODO);

        when(task2.getTitle()).thenReturn("Task 2");
        when(task2.getDescription()).thenReturn("Desc 2");
        when(task2.getStatus()).thenReturn(TaskStatus.COMPLETED);

        Page<Task> mockPage = new Page<>(List.of(task1, task2), page, size, totalElements);
        when(taskRepository.findAll(page, size)).thenReturn(mockPage);

        Page<Response> result = useCase.execute(page, size);

        assertEquals(2, result.content().size());
        assertEquals(page, result.page());
        assertEquals(size, result.size());
        assertEquals(totalElements, result.totalElements());

        Response r1 = result.content().getFirst();
        assertEquals("Task 1", r1.title());
        assertEquals("Desc 1", r1.description());
        assertEquals(TODO, r1.status());

        Response r2 = result.content().get(1);
        assertEquals("Task 2", r2.title());
        assertEquals("Desc 2", r2.description());
        assertEquals(COMPLETED, r2.status());

        verify(taskRepository).findAll(page, size);
    }

    @Test
    void shouldListTasksByStatusSuccessfully() {
        int page = 0;
        int size = 1;
        long totalElements = 1;

        Task task = mock(Task.class);

        when(task.getTitle()).thenReturn("Only Task");
        when(task.getDescription()).thenReturn("Filtered Desc");
        when(task.getStatus()).thenReturn(COMPLETED);

        Page<Task> mockPage = new Page<>(List.of(task), page, size, totalElements);
        when(taskRepository.findAll(COMPLETED, page, size)).thenReturn(mockPage);

        Page<Response> result = useCase.execute(COMPLETED, page, size);

        assertEquals(1, result.content().size());

        Response r = result.content().getFirst();
        assertEquals("Only Task", r.title());
        assertEquals("Filtered Desc", r.description());
        assertEquals(COMPLETED, r.status());

        verify(taskRepository).findAll(COMPLETED, page, size);
    }

}
