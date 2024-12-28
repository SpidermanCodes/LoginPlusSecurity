package com.example.LoginPlusSecurity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LoginPlusSecurity.model.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    
    Optional<BlogPost> findBySlug(String slug);

    List<BlogPost> findByTitleContainingIgnoreCase(String title); // For search

    Page<BlogPost> findByTitleContainingIgnoreCase(String title, Pageable pageable); // For filter

}
