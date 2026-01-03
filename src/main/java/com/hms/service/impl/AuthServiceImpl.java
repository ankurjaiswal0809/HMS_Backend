package com.hms.service.impl;

import com.hms.config.JwtUtil;
import com.hms.dto.LoginRequest;
import com.hms.dto.LoginResponse;
import com.hms.entity.User;
import com.hms.exception.UnauthorizedException;
import com.hms.repository.UserRepository;
import com.hms.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        // ✅ CHECK IF USER IS ENABLED
        if (!user.getEnabled()) {
            throw new UnauthorizedException("User account is disabled");
        }

        // ✅ CHECK PASSWORD (BCrypt)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        // ✅ GENERATE JWT WITH ROLE
        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );

        return new LoginResponse(token);
    }


}
