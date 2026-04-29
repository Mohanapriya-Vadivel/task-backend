package com.priya.task_management_system.service.impl;

import com.priya.task_management_system.dto.task.TaskRequestDto;
import com.priya.task_management_system.dto.task.TaskResponseDto;
import com.priya.task_management_system.entity.Task;
import com.priya.task_management_system.entity.User;
import com.priya.task_management_system.enums.Role;
import com.priya.task_management_system.enums.TaskPriority;
import com.priya.task_management_system.enums.TaskStatus;
import com.priya.task_management_system.exception.ResourceNotFoundException;
import com.priya.task_management_system.repository.TaskRepository;
import com.priya.task_management_system.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import com.priya.task_management_system.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private TaskResponseDto mapToResponse(Task task){
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())

                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .assignedUserId(
                        task.getAssignedUser()!=null?
                                task.getAssignedUser().getId():null
                )
                .assignedUserName(
                        task.getAssignedUser()!=null?
                                task.getAssignedUser().getName() : null
                )
                .createdById(
                        task.getCreatedBy()!=null?
                                task.getCreatedBy().getId():null
                )
                .createdByName(
                        task.getCreatedBy()!=null?
                                task.getCreatedBy().getName():null
                )
                .build();
    }
    @Override
    public TaskResponseDto createTask(TaskRequestDto request) {
        User assignedUser = null;


        User currentUser = getCurrentUser();
        if(currentUser.getRole()== Role.ADMIN){
            if (request.getAssignedUserId() != null) {
                assignedUser = userRepository.findById(request.getAssignedUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("Assigned user not found"));
            }else {
                assignedUser = currentUser;
            }
        }else{
            assignedUser=currentUser;
        }

        Task task=Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .assignedUser(assignedUser)
                .createdBy(currentUser)
                .build();
        Task savedTask=taskRepository.save(task);
        return mapToResponse(savedTask);
    }

    public List<TaskResponseDto> getTasksByUserId(Long userId,int page,int size){
        Pageable pageable= PageRequest.of(page,size);
        return taskRepository.findByAssignedUserId(userId,pageable)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TaskResponseDto> getAllTasks(int page, int size, TaskStatus status, TaskPriority priority,String title) {
        User currentUser = getCurrentUser();
        Pageable pageable= PageRequest.of(page,size);
        if(status != null && priority != null && title != null){
            if (currentUser.getRole() == Role.ADMIN) {
                return taskRepository.findByStatusAndPriorityAndTitleContainingIgnoreCase(status, priority, pageable,title)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();
            }

            return taskRepository.findByCreatedByAndStatusAndPriorityAndTitleContainingIgnoreCase(currentUser, status, priority, pageable,title)
                    .stream()
                    .map(this::mapToResponse)
                    .toList();
        }
        if (status != null && priority != null) {
            if (currentUser.getRole() == Role.ADMIN) {
                return taskRepository.findByStatusAndPriority(status, priority, pageable)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();
            }

            return taskRepository.findByCreatedByAndStatusAndPriority(currentUser, status, priority, pageable)
                    .stream()
                    .map(this::mapToResponse)
                    .toList();
        }
        if(status!=null){
            if(currentUser.getRole()== Role.ADMIN){
               return taskRepository.findByStatus(status,pageable)
                       .stream()
                       .map(this::mapToResponse)
                       .toList();
           }
            return taskRepository.findByCreatedByAndStatus(currentUser,status,pageable)
                    .stream()
                    .map(this::mapToResponse)
                    .toList();

       }
        if(priority!=null){
            if(currentUser.getRole()== Role.ADMIN){
                return taskRepository.findByPriority(priority,pageable)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();
            }
            return taskRepository.findByCreatedByAndPriority(currentUser,priority,pageable)
                    .stream()
                    .map(this::mapToResponse)
                    .toList();

        }
        if(title!=null){
            if(currentUser.getRole()== Role.ADMIN){
                return taskRepository.findByTitleContainingIgnoreCase(title,pageable)
                        .stream()
                        .map(this::mapToResponse)
                        .toList();
            }
            return taskRepository.findByCreatedByAndTitleContainingIgnoreCase(currentUser,title,pageable)
                    .stream()
                    .map(this::mapToResponse)
                    .toList();


        }

        if(currentUser.getRole()== Role.ADMIN){
            return taskRepository.findAll(pageable)
                    .stream()
                    .map(this::mapToResponse)
                    .toList();
        }
        return taskRepository.findByCreatedBy(currentUser,pageable)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }


    @Override
    public TaskResponseDto getTaskById(Long id){
        Task task=taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Task not found"));
        return mapToResponse(task);
    }

    @Override
    public TaskResponseDto updateTask(Long id, TaskRequestDto request){
        Task task=taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Task not found"));
        User assignedUser = null;

        if (request.getAssignedUserId() != null) {
            assignedUser = userRepository.findById(request.getAssignedUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assigned user not found"));
        }
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setPriority(request.getPriority());
        task.setDueDate(request.getDueDate());
        task.setAssignedUser(assignedUser);
        Task updatedTask=taskRepository.save(task);
        return mapToResponse(updatedTask);
    }

    @Override
    public void deleteTask(Long id){
        Task task=taskRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);

    }
}