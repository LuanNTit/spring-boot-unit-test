package com.luan.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luan.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductName(String productName);
}