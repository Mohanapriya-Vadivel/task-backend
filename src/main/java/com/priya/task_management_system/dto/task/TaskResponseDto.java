package com.priya.task_management_system.dto.task;

import com.priya.task_management_system.enums.TaskPriority;
import com.priya.task_management_system.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;

    private Long assignedUserId;
    private String assignedUserName;

    private Long createdById;
    private String createdByName;
}