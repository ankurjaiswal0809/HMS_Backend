package com.hms.bootstrap;

import com.hms.entity.User;
import com.hms.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBootstrap {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//    @PostConstruct
//    public void init() {
//        if (userRepository.findByUsername("admin").isEmpty()) {
//            User admin = new User();
//            admin.setUsername("admin");
//            admin.setPassword(passwordEncoder.encode("admin123"));
//            admin.setRole("ADMIN");
//            admin.setEnabled(true);
//            userRepository.save(admin);
//
//            System.out.println("✅ ADMIN CREATED SUCCESSFULLY");
//        }
//    }
}
