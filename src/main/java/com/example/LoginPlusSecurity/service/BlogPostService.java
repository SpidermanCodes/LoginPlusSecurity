package com.example.LoginPlusSecurity.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.LoginPlusSecurity.model.BlogPost;
import com.example.LoginPlusSecurity.repository.BlogPostRepository;

@Service
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    // public List<BlogPost> getAllBlogPosts() {
    //     return blogPostRepository.findAll();
    // }

    public Page<BlogPost> getPaginatedBlogPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("creationDate").descending());
        return blogPostRepository.findAll(pageable);
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
