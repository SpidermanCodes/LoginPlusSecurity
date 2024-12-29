package com.example.LoginPlusSecurity.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.LoginPlusSecurity.model.BlogPost;
import com.example.LoginPlusSecurity.model.Comment;
import com.example.LoginPlusSecurity.repository.BlogPostRepository;
import com.example.LoginPlusSecurity.repository.CommentRepository;

@RestController
@RequestMapping("/comments") // spring security config
public class CommentController {

    private final CommentRepository commentRepository;
    private final BlogPostRepository blogPostRepository;

    public CommentController(CommentRepository commentRepository, BlogPostRepository blogPostRepository) {
        this.commentRepository = commentRepository;
        this.blogPostRepository = blogPostRepository;
    }

    @PostMapping("/add/{blogPostId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addComment(@PathVariable Long blogPostId, 
                           @RequestParam String content, 
                           Principal principal) {
        BlogPost blogPost = blogPostRepository.findById(blogPostId)
                .orElseThrow(() -> new RuntimeException("Blog post not found!"));

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBlogPost(blogPost);
        comment.setAuthorName(principal != null ? principal.getName() : "Anonymous");
        commentRepository.save(comment);
    }

    @GetMapping("/list/{blogPostId}")
    public List<Comment> listComments(@PathVariable Long blogPostId) {
        return commentRepository.findByBlogPostId(blogPostId);
    }
}
