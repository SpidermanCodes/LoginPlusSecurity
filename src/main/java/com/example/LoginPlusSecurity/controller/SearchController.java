// package com.example.LoginPlusSecurity.controller;

// import java.util.List;

// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.example.LoginPlusSecurity.model.BlogPost;
// import com.example.LoginPlusSecurity.repository.BlogPostRepository;

// @Controller
// public class SearchController {

//     private final BlogPostRepository blogPostRepository;

//     public SearchController(BlogPostRepository blogPostRepository) {
//         this.blogPostRepository = blogPostRepository;
//     }

//     @GetMapping("/search")
//     public String searchBlogPosts(@RequestParam("query") String query, Model model) {
//         List<BlogPost> searchResults = blogPostRepository.findByTitleContainingIgnoreCase(query);
//         model.addAttribute("blogPosts", searchResults);
//         model.addAttribute("query", query);
//         return "searchResults";
//     }
// }
