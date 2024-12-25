package com.example.LoginPlusSecurity.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.example.LoginPlusSecurity.model.BlogPost;
import com.example.LoginPlusSecurity.repository.CategoryRepository;
import com.example.LoginPlusSecurity.service.BlogPostService;

@Controller
public class BlogPostController {

    private final BlogPostService blogPostService;
    private final CategoryRepository categoryRepository;

    public BlogPostController(BlogPostService blogPostService, CategoryRepository categoryRepository) {
        this.blogPostService = blogPostService;
        this.categoryRepository = categoryRepository;
    }

    
    @GetMapping("/admin/manage")
    public String home() {
        return "manage";
    }


    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("blogPosts", blogPostService.getAllBlogPosts());
        return "home";
    }


    @GetMapping("/blog/{slug}")
    public String viewBlogPost(@PathVariable String slug, Model model) {
        try {
            BlogPost blogPost = blogPostService.findBySlug(slug);
            model.addAttribute("blogPost", blogPost);
            return "viewBlog";
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());  
        }
    }


    @GetMapping("/admin/blog/create")
    public String showCreateBlogForm(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        model.addAttribute("categories", categoryRepository.findAll());
        return "createBlog";
    }


    @PostMapping("/admin/blog/create")
    public String createBlogPost(@ModelAttribute BlogPost blogPost, Principal principal) {
        blogPost.setAuthorName(principal.getName()); // Fetch the logged-in user's name
        blogPostService.saveBlogPost(blogPost);
        return "redirect:/"; // 
    }
}
