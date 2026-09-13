package com.milestone.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String email;
    private String currentPassword;
    private String newPassword;
}
