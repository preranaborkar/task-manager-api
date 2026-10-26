package com.prerana.taskmanager.service;

import com.prerana.taskmanager.dto.TaskRequest;
import com.prerana.taskmanager.dto.TaskResponse;
import com.prerana.taskmanager.exception.ResourceNotFoundException;
import com.prerana.taskmanager.model.Task;
import com.prerana.taskmanager.model.TaskStatus;
import com.prerana.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle().trim());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus() : TaskStatus.PENDING);
        task.setDueDate(request.getDueDate());
        return new TaskResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasks(TaskStatus status, String keyword) {
        List<Task> tasks;
        if (keyword != null && !keyword.isBlank()) {
            tasks = taskRepository.findByTitleContainingIgnoreCase(keyword.trim());
            if (status != null) {
                tasks = tasks.stream().filter(t -> t.getStatus() == status).toList();
            }
        } else if (status != null) {
            tasks = taskRepository.findByStatus(status);
        } else {
            tasks = taskRepository.findAll();
        }
        return tasks.stream().map(TaskResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        return new TaskResponse(findOrThrow(id));
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = findOrThrow(id);
        task.setTitle(request.getTitle().trim());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        task.setDueDate(request.getDueDate());
        return new TaskResponse(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.delete(findOrThrow(id));
    }

    private Task findOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
    }
}
