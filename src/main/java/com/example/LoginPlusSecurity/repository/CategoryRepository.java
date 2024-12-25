package com.example.LoginPlusSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LoginPlusSecurity.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
