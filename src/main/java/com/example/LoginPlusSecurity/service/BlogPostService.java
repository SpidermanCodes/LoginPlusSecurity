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
        String slug = generateSlug(blogPost.getTitle());
        blogPost.setSlug(slug);
        blogPostRepository.save(blogPost);
    }

    public BlogPost findBySlug(String slug) {
        return blogPostRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Blog post not found!"));
    }

    private String generateSlug(String title) {
        String slug = title.toLowerCase() // Convert to lowercase
                        .replaceAll("[^a-z0-9\\s-]", "") // Remove invalid characters
                        .trim() // Remove leading and trailing spaces
                        .replaceAll("\\s+", "-"); // Replace spaces with "-"
        return slug.replaceAll("^-|-$", ""); // Remove leading/trailing "-"
    }


}
