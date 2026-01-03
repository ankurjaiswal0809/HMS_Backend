package com.hms.config;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordDebug {

    @PostConstruct
    public void testPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        boolean result = encoder.matches(
            "admin123",
            "$2a$10$44xJvd6DHsWryTAE0zwmBOLuhbbIpGzaw/OlgAXP7OoW7cV3bWFGO"
        );

        System.out.println("🔐 BCrypt match result = " + result);
    }
}
