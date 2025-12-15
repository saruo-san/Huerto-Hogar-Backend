package com.huertohogar.huerto_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.huertohogar.huerto_backend.model.Product;
import com.huertohogar.huerto_backend.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
