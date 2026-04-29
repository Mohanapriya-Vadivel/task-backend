package com.priya.task_management_system.dto.user;

import com.priya.task_management_system.enums.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDto {
    private Long id;

    private String name;

    private String email;

    private Role role;
}
