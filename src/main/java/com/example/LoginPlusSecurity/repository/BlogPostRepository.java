package com.example.LoginPlusSecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LoginPlusSecurity.model.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    
    Optional<BlogPost> findBySlug(String slug);
}
