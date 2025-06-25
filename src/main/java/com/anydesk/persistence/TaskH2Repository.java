package com.anydesk.persistence;

import com.anydesk.domain.model.Task;
import com.anydesk.domain.model.TaskStatus;
import com.anydesk.domain.repository.TaskRepository;
import com.anydesk.domain.util.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class TaskH2Repository implements TaskRepository {

    @Override
    public Page<Task> findAll(TaskStatus status, int page, int size) {
        return null;
    }

    @Override
    public Page<Task> findAll(int page, int size) {
        return null;
    }

    @Override
    public Task save(Task task) {
        return null;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

}
