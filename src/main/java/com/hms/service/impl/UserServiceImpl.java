package com.hms.service.impl;

import com.hms.dto.CreateUserRequest;
import com.hms.dto.UserResponse;
import com.hms.entity.User;
import com.hms.repository.UserRepository;
import com.hms.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setEnabled(true);

        userRepository.save(user);

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getEnabled()
        );
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getRole(),
                        u.getEnabled()))
                .toList();
    }

    @Override
    public UserResponse enableOrDisableUser(Long userId, Boolean enabled) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(enabled);
        userRepository.save(user);

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getEnabled()
        );
    }

    @Override
    public void deleteUser(Long userId) {

        String currentUsername = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow();

        if (currentUser.getId().equals(userId)) {
            throw new RuntimeException("Admin cannot delete himself");
        }
        //currentUser.setDeleted(true);
        //userRepository.save(currentUser);
        userRepository.deleteById(userId);
    }

}
