package com.anydesk.persistence.repository;

import com.anydesk.domain.model.Task;
import com.anydesk.domain.model.TaskStatus;
import com.anydesk.domain.repository.TaskRepository;
import com.anydesk.domain.util.Page;
import com.anydesk.persistence.dao.TaskDAO;
import com.anydesk.persistence.mapper.TaskMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class TaskH2Repository implements TaskRepository {

    @Inject
    TaskMapper taskMapper;

    @Override
    public Page<Task> findAll(TaskStatus status, int page, int size) {
        PanacheQuery<TaskDAO> query = TaskDAO.find("status", status);
        query.page(page, size);

        long total = query.count();
        var content = query.list();

        return new Page<>(content, page, size, total)
                .map(taskMapper::toDomain);
    }

    @Override
    public Page<Task> findAll(int page, int size) {
        PanacheQuery<TaskDAO> query = TaskDAO.findAll();
        query.page(page, size);

        long total = query.count();
        var content = query.list();

        return new Page<>(content, page, size, total)
                .map(taskMapper::toDomain);
    }

    @Override
    @Transactional
    public Task save(Task task) {
        TaskDAO entity = taskMapper.toEntity(task);
        if (task.getPersistenceId() != null) {
            entity.id = task.getPersistenceId();
            entity = TaskDAO.getEntityManager().merge(entity);
        } else {
            entity.persist();
        }
        return taskMapper.toDomain(entity);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return TaskDAO.findByIdOptional(id)
                .map(entity -> taskMapper.toDomain((TaskDAO) entity));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        TaskDAO.deleteById(id);
    }

}