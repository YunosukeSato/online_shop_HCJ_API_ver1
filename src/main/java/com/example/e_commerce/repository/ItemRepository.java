package com.example.e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_commerce.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{

	Optional<Item> findById(int id);
    Optional<Item> findByName(String name);
    Optional<Item> findByBrand(String brand);
    Boolean existsByName(String name);
}
