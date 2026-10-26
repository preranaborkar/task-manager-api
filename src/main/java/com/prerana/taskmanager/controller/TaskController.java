package com.prerana.taskmanager.controller;

import com.prerana.taskmanager.dto.TaskRequest;
import com.prerana.taskmanager.dto.TaskResponse;
import com.prerana.taskmanager.model.TaskStatus;
import com.prerana.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // POST /api/tasks
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    // GET /api/tasks  |  /api/tasks?status=PENDING  |  /api/tasks?keyword=java
    @GetMapping
    public List<TaskResponse> getTasks(@RequestParam(required = false) TaskStatus status,
                                       @RequestParam(required = false) String keyword) {
        return taskService.getTasks(status, keyword);
    }

    // GET /api/tasks/{id}
    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    // PUT /api/tasks/{id}
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        return taskService.updateTask(id, request);
    }

    // DELETE /api/tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
