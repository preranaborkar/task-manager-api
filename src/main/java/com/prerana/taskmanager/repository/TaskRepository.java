package com.prerana.taskmanager.repository;

import com.prerana.taskmanager.model.Task;
import com.prerana.taskmanager.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitleContainingIgnoreCase(String keyword);
}
