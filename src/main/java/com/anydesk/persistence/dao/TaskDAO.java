package com.anydesk.persistence.dao;

import com.anydesk.domain.model.TaskStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "TASK")
public class TaskDAO extends PanacheEntity {

    public String title;
    public String description;

    @Enumerated(EnumType.STRING)
    public TaskStatus status;

}
