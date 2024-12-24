package com.example.LoginPlusSecurity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.LoginPlusSecurity.model.BlogPost;
import com.example.LoginPlusSecurity.repository.BlogPostRepository;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    public void saveBlogPost(BlogPost blogPost) {
        blogPostRepository.save(blogPost);
    }
}
