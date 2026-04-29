package com.priya.task_management_system.service;

import com.priya.task_management_system.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    void deleteUser(Long id);
}
