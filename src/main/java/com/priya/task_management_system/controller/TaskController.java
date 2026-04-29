package com.priya.task_management_system.controller;

import com.priya.task_management_system.dto.task.TaskRequestDto;
import com.priya.task_management_system.dto.task.TaskResponseDto;
import com.priya.task_management_system.enums.TaskPriority;
import com.priya.task_management_system.enums.TaskStatus;
import com.priya.task_management_system.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("create")
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto request) {
        return taskService.createTask(request);
    }

    @GetMapping("get")
    public List<TaskResponseDto> getAllTasks (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size,
            @RequestParam (required = false)TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) String title){
        return taskService.getAllTasks(page,size,status,priority,title);
    }

    @GetMapping("/user/{userId}")
    public List<TaskResponseDto> getTasksByUserId(@PathVariable Long userId, @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "1000") int size){

        return taskService.getTasksByUserId(userId,page,size);
    }

    @GetMapping("get/{id}")
    public TaskResponseDto getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }



    @PutMapping("update/{id}")
    public TaskResponseDto updateTask(@PathVariable Long id,@RequestBody TaskRequestDto request){
        return taskService.updateTask(id,request);
    }

    @DeleteMapping("delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}