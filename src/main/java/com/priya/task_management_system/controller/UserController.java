package com.priya.task_management_system.controller;

import com.priya.task_management_system.dto.user.UserResponseDto;
import com.priya.task_management_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public List<UserResponseDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("delete/{id}")
    public void deleteTask(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
