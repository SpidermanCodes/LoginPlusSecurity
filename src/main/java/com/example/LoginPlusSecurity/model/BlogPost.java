package com.example.LoginPlusSecurity.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10000)
    private String content;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false, unique = true)
    private String slug;

    public String getExcerpt() {
        String[] words = content.split("\\s+");
        return words.length > 50 ? String.join(" ", java.util.Arrays.copyOf(words, 50)) + "..." : content;
    }

}