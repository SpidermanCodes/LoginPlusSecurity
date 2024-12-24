package com.example.LoginPlusSecurity.repository;

import com.example.LoginPlusSecurity.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}
