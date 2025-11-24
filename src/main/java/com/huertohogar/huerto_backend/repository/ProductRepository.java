package com.huertohogar.huerto_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huertohogar.huerto_backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
