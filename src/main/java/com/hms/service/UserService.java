package com.hms.service;

import com.hms.dto.CreateUserRequest;
import com.hms.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse enableOrDisableUser(Long userId, Boolean enabled);

    void deleteUser(Long userId);
}
