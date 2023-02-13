package com.example.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_commerce.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>{

    Optional<Cart> findByName(String name);
    Optional<Cart> findByBrand(String brand);
    Boolean existsByName(String name);
}
