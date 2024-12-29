package com.example.LoginPlusSecurity.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.LoginPlusSecurity.model.BlogPost;
import com.example.LoginPlusSecurity.repository.BlogPostRepository;
import com.example.LoginPlusSecurity.repository.CategoryRepository;
import com.example.LoginPlusSecurity.repository.CommentRepository;
import com.example.LoginPlusSecurity.service.BlogPostService;

@Controller
public class BlogPostController {

    private final BlogPostService blogPostService;
    private final CategoryRepository categoryRepository;
    private final BlogPostRepository blogPostRepository;
    private final CommentRepository commentRepository;


    public BlogPostController(BlogPostService blogPostService, CategoryRepository categoryRepository, BlogPostRepository blogPostRepository, CommentRepository commentRepository) {
        this.blogPostService = blogPostService;
        this.categoryRepository = categoryRepository;
        this.blogPostRepository = blogPostRepository;
        this.commentRepository = commentRepository;
    }

    
    @GetMapping("/admin/manage")
    public String home() {
        return "manage";
    }


    @GetMapping("/")
        public String showHomePage(@RequestParam(defaultValue = "latest") String filter, 
                                @RequestParam(defaultValue = "0") int page, 
                                Model model) {
            int size = 5; // Number of blog per page
            Page<BlogPost> blogPosts = blogPostService.getFilteredBlogPosts(page, size, filter);
            model.addAttribute("blogPosts", blogPosts.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", blogPosts.getTotalPages());
            model.addAttribute("filter", filter);
            return "home";
        }

        @GetMapping("/search")
        public String searchBlogPosts(@RequestParam("query") String query, 
                                    @RequestParam(defaultValue = "latest") String filter, 
                                    @RequestParam(defaultValue = "0") int page, 
                                    Model model) {
            int size = 5; // Fixed size
            Page<BlogPost> blogPosts = blogPostService.searchFilteredBlogPosts(query, page, size, filter);
            model.addAttribute("blogPosts", blogPosts.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", blogPosts.getTotalPages());
            model.addAttribute("query", query);
            model.addAttribute("filter", filter);
            return "searchResults";
        }
    

    @GetMapping("/page/{pageNumber}")
    public String showPaginatedHomePage(@PathVariable int pageNumber, Model model) {
        int size = 5; // Fixed size, 5 blog/page
        Page<BlogPost> blogPosts = blogPostService.getPaginatedBlogPosts(pageNumber, size);
        model.addAttribute("blogPosts", blogPosts.getContent());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", blogPosts.getTotalPages());
        return "home";
    }


    @GetMapping("/blog/{slug}")
    public String viewBlogPost(@PathVariable String slug, Model model) {
        try {
            BlogPost blogPost = blogPostService.findBySlug(slug);
            blogPost.setViews(blogPost.getViews() + 1);
            blogPostRepository.save(blogPost); // Save updated view count
            model.addAttribute("blogPost", blogPost);
            model.addAttribute("comments", commentRepository.findByBlogPostId(blogPost.getId()));
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