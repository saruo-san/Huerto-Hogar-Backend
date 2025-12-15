package com.huertohogar.huerto_backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huertohogar.huerto_backend.model.Role;
import com.huertohogar.huerto_backend.model.User;
import com.huertohogar.huerto_backend.repository.UserRepository;
import com.huertohogar.huerto_backend.security.JwtService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://18.232.152.111"})
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> payload) {
        String nombre = payload.getOrDefault("nombre", "");
        String correo = payload.getOrDefault("correo", "");
        String password = payload.getOrDefault("password", "");

        if (correo.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Correo y password son requeridos"));
        }
        if (userRepository.findByCorreo(correo).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Correo ya registrado"));
        }
        User user = new User(nombre, correo, passwordEncoder.encode(password), Role.USER);
        userRepository.save(user);
        String token = jwtService.generateToken(user.getCorreo(), user.getRole());
        return ResponseEntity.ok(Map.of(
            "token", token,
            "user", Map.of("id", user.getId(), "nombre", user.getNombre(), "correo", user.getCorreo(), "role", user.getRole().name())
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String correo = payload.getOrDefault("correo", "");
        String password = payload.getOrDefault("password", "");
        return userRepository.findByCorreo(correo)
            .filter(u -> passwordEncoder.matches(password, u.getPasswordHash()))
            .map(u -> {
                String token = jwtService.generateToken(u.getCorreo(), u.getRole());
                return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user", Map.of("id", u.getId(), "nombre", u.getNombre(), "correo", u.getCorreo(), "role", u.getRole().name())
                ));
            })
            .orElse(ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas")));
    }
}
