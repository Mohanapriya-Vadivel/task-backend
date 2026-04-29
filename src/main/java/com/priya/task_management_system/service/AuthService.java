package com.priya.task_management_system.service;

import com.priya.task_management_system.dto.auth.AuthResponseDto;
import com.priya.task_management_system.dto.auth.LoginRequestDto;
import com.priya.task_management_system.dto.auth.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}