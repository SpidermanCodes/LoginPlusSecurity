package com.example.LoginPlusSecurity.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.LoginPlusSecurity.model.BlogPost;
import com.example.LoginPlusSecurity.service.BlogPostService;

@Controller
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/admin/manage")
    public String home() {
        return "manage";
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        // TODO 1: return only 50 words excerpts, slug method on "read more"
        model.addAttribute("blogPosts", blogPostService.getAllBlogPosts());
        return "home";
    }

    @GetMapping("/admin/blog/create")
    public String showCreateBlogForm(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        return "createBlog";
    }

    @PostMapping("/admin/blog/create")
    public String createBlogPost(@ModelAttribute BlogPost blogPost, Principal principal) {
        blogPost.setAuthorName(principal.getName()); // Fetch the logged-in user's name
        blogPostService.saveBlogPost(blogPost);
        return "redirect:/"; // 
    }
}
