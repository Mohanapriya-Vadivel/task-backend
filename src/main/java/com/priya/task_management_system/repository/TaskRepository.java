package com.priya.task_management_system.repository;

import com.priya.task_management_system.entity.Task;
import com.priya.task_management_system.entity.User;
import com.priya.task_management_system.enums.TaskPriority;
import com.priya.task_management_system.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByAssignedUser(User user);

    Page<Task> findByCreatedBy(User user, Pageable pageable);

    Page<Task> findByStatus( TaskStatus status,Pageable pageable);

    Page<Task> findByCreatedByAndStatus(User user, TaskStatus status, Pageable pageable);

    Page<Task> findByPriority(TaskPriority priority, Pageable pageable);

    Page<Task> findByAssignedUserId(Long userId,Pageable pageable);

    Page<Task> findByCreatedByAndPriority(User user, TaskPriority priority, Pageable pageable);

    Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Task> findByCreatedByAndTitleContainingIgnoreCase(User user, String title, Pageable pageable);

    Page<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority, Pageable pageable);

    Page<Task> findByCreatedByAndStatusAndPriority(
            User user,
            TaskStatus status,
            TaskPriority priority,
            Pageable pageable
    );

    Page<Task> findByStatusAndPriorityAndTitleContainingIgnoreCase(TaskStatus status, TaskPriority priority, Pageable pageable,String title);

    Page<Task> findByCreatedByAndStatusAndPriorityAndTitleContainingIgnoreCase(User user,TaskStatus status, TaskPriority priority, Pageable pageable,String title);

    void  deleteByAssignedUserId(Long id);

    void deleteByCreatedById(Long id);

}
