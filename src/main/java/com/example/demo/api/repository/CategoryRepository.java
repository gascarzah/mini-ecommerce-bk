package com.example.demo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.api.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
