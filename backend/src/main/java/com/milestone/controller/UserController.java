package com.milestone.controller;

import com.milestone.dto.UserProfileDto;
import com.milestone.model.User;
import com.milestone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.milestone.service.UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        return ResponseEntity.ok(UserProfileDto.fromEntity(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileDto> updateUser(Authentication authentication, @RequestBody com.milestone.dto.UpdateUserRequest request) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        User updatedUser = userService.updateUser(user, request);
        return ResponseEntity.ok(UserProfileDto.fromEntity(updatedUser));
    }
}
