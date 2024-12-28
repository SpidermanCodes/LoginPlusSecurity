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


    public Page<BlogPost> getFilteredBlogPosts(int page, int size, String sortBy) {
        Sort sort;
        switch (sortBy) {
            case "oldest":
                sort = Sort.by("creationDate").ascending();
                break;
            case "views":
                sort = Sort.by("views").descending();
                break;
            default: // latest
                sort = Sort.by("creationDate").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return blogPostRepository.findAll(pageable);
    }

    public Page<BlogPost> searchFilteredBlogPosts(String query, int page, int size, String sortBy) {
        Sort sort;
        switch (sortBy) {
            case "oldest":
                sort = Sort.by("creationDate").ascending();
                break;
            case "views":
                sort = Sort.by("views").descending();
                break;
            default: // latest blog
                sort = Sort.by("creationDate").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        return blogPostRepository.findByTitleContainingIgnoreCase(query, pageable);
    }



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
