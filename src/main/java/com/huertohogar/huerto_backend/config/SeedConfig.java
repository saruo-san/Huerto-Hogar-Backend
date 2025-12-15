package com.huertohogar.huerto_backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.huertohogar.huerto_backend.model.Role;
import com.huertohogar.huerto_backend.model.User;
import com.huertohogar.huerto_backend.repository.UserRepository;

@Configuration
public class SeedConfig {

    @Bean
    CommandLineRunner seedAdmin(UserRepository users) {
        return args -> {
            if (users.findByCorreo("admin@demo.com").isEmpty()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                User admin = new User("Administrador", "admin@demo.com", encoder.encode("Admin123!"), Role.ADMIN);
                users.save(admin);
            }
        };
    }
}
