package com.prerana.taskmanager.dto;

import com.prerana.taskmanager.model.Task;
import com.prerana.taskmanager.model.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final LocalDate dueDate;
    private final LocalDateTime createdAt;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.dueDate = task.getDueDate();
        this.createdAt = task.getCreatedAt();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
