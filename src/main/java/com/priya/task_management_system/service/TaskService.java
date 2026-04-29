package com.priya.task_management_system.service;

import com.priya.task_management_system.dto.task.TaskRequestDto;
import com.priya.task_management_system.dto.task.TaskResponseDto;
import com.priya.task_management_system.enums.TaskPriority;
import com.priya.task_management_system.enums.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskResponseDto createTask(TaskRequestDto request);

    List<TaskResponseDto> getAllTasks(int page, int size, TaskStatus status, TaskPriority priority,String title);

    List<TaskResponseDto> getTasksByUserId(Long userId,int page,int size);

    TaskResponseDto getTaskById(Long id);

    TaskResponseDto updateTask(Long id, TaskRequestDto request);

    void deleteTask(Long id);
}