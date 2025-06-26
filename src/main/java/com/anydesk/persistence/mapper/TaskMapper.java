package com.anydesk.persistence.mapper;

import com.anydesk.domain.model.Task;
import com.anydesk.persistence.dao.TaskDAO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskMapper {

    public Task toDomain(TaskDAO dao) {
        Task task = new Task(dao.title, dao.description, dao.status);
        task.setPersistenceId(dao.id);
        return task;
    }

    public TaskDAO toEntity(Task task) {
        TaskDAO dao = new TaskDAO();
        dao.title = task.getTitle();
        dao.description = task.getDescription();
        dao.status = task.getStatus();
        return dao;
    }

}
