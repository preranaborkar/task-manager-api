package com.prerana.taskmanager.service;

import com.prerana.taskmanager.dto.TaskRequest;
import com.prerana.taskmanager.dto.TaskResponse;
import com.prerana.taskmanager.exception.ResourceNotFoundException;
import com.prerana.taskmanager.model.Task;
import com.prerana.taskmanager.model.TaskStatus;
import com.prerana.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_defaultsStatusToPending() {
        TaskRequest request = new TaskRequest();
        request.setTitle("  Learn Spring Boot ");
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        TaskResponse response = taskService.createTask(request);

        assertEquals("Learn Spring Boot", response.getTitle());
        assertEquals(TaskStatus.PENDING, response.getStatus());
    }

    @Test
    void getTaskById_throwsWhenMissing() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(99L));
    }

    @Test
    void getTasks_filtersByStatus() {
        Task task = new Task("Practice SQL", "Joins", TaskStatus.IN_PROGRESS, null);
        when(taskRepository.findByStatus(TaskStatus.IN_PROGRESS)).thenReturn(List.of(task));

        List<TaskResponse> result = taskService.getTasks(TaskStatus.IN_PROGRESS, null);

        assertEquals(1, result.size());
        assertEquals("Practice SQL", result.get(0).getTitle());
    }

    @Test
    void updateTask_changesFields() {
        Task existing = new Task("Old", "old desc", TaskStatus.PENDING, null);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        TaskRequest request = new TaskRequest();
        request.setTitle("New");
        request.setStatus(TaskStatus.COMPLETED);

        TaskResponse response = taskService.updateTask(1L, request);

        assertEquals("New", response.getTitle());
        assertEquals(TaskStatus.COMPLETED, response.getStatus());
    }

    @Test
    void deleteTask_deletesExisting() {
        Task existing = new Task("Delete me", null, TaskStatus.PENDING, null);
        when(taskRepository.findById(5L)).thenReturn(Optional.of(existing));

        taskService.deleteTask(5L);

        verify(taskRepository).delete(existing);
    }
}
