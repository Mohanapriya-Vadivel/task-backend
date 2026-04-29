package com.priya.task_management_system.service.impl;

import com.priya.task_management_system.dto.task.TaskResponseDto;
import com.priya.task_management_system.dto.user.UserResponseDto;
import com.priya.task_management_system.entity.Task;
import com.priya.task_management_system.entity.User;
import com.priya.task_management_system.exception.ResourceNotFoundException;
import com.priya.task_management_system.repository.TaskRepository;
import com.priya.task_management_system.repository.UserRepository;
import com.priya.task_management_system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private UserResponseDto mapToResponse(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

    }

    @Override
    public List<UserResponseDto> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        taskRepository.deleteByAssignedUserId(id);
        taskRepository.deleteByCreatedById(id);

        userRepository.delete(user);
    }
}
