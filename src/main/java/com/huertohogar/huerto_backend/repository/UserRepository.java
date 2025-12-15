package com.huertohogar.huerto_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huertohogar.huerto_backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCorreo(String correo);
}
