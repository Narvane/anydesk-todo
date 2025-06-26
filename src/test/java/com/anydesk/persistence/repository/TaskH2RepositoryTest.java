package com.anydesk.persistence.repository;

import com.anydesk.domain.model.Task;
import com.anydesk.domain.model.TaskStatus;
import com.anydesk.domain.util.Page;
import com.anydesk.persistence.dao.TaskDAO;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TaskH2RepositoryTest {

    @Inject
    TaskH2Repository repository;

    Task task1;
    Task task2;

    @BeforeEach
    @Transactional
    void setup() {
        TaskDAO.deleteAll();
        task1 = new Task("Task 1", "Desc 1", TaskStatus.TODO);
        task2 = new Task("Task 2", "Desc 2", TaskStatus.COMPLETED);
    }

    @Test
    void testSaveAndFindById() {
        Task saved = repository.save(task1);
        assertNotNull(saved.getPersistenceId());

        Optional<Task> found = repository.findById(saved.getPersistenceId());
        assertTrue(found.isPresent());
        assertEquals("Task 1", found.get().getTitle());
    }

    @Test
    void testUpdateExistingTask() {
        Task created = repository.save(task1);
        Long id = created.getPersistenceId();

        Task updated = new Task(id, "Updated", "Nova Desc", TaskStatus.COMPLETED);
        Task result = repository.save(updated);

        assertEquals(id, result.getPersistenceId());
        assertEquals("Updated", result.getTitle());

        Optional<Task> loaded = repository.findById(id);
        assertTrue(loaded.isPresent());
        assertEquals("COMPLETED", loaded.get().getStatus().name());
    }

    @Test
    void testDelete() {
        Task created = repository.save(task1);
        Long id = created.getPersistenceId();

        repository.deleteById(id);
        assertTrue(repository.findById(id).isEmpty());
    }

    @Test
    void testFindAllPaged() {
        repository.save(task1);
        repository.save(task2);

        Page<Task> page = repository.findAll(0, 10);
        assertEquals(2, page.content().size());
        assertEquals(0, page.page());
        assertEquals(10, page.size());
        assertEquals(2, page.totalElements());
    }

    @Test
    void testFindAllByStatusPaged() {
        repository.save(task1);
        repository.save(task2);

        Page<Task> page = repository.findAll(TaskStatus.TODO, 0, 10);
        assertEquals(1, page.content().size());
        assertEquals(TaskStatus.TODO, page.content().getFirst().getStatus());
    }

}
