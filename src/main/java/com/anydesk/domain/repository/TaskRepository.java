package com.anydesk.domain.repository;

import com.anydesk.domain.model.Task;
import com.anydesk.domain.model.TaskStatus;
import com.anydesk.domain.util.Page;

import java.util.Optional;

public interface TaskRepository {

    Page<Task> findAll(TaskStatus status, int page, int size);

    Page<Task> findAll(int page, int size);

    Task save(Task task);

    Optional<Task> findById(Long id);

    void deleteById(Long id);

}
