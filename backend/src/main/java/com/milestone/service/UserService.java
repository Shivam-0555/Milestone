package com.milestone.service;

import com.milestone.model.User;
import com.milestone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String email, String rawPassword) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Username or email already in use");
        }
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
