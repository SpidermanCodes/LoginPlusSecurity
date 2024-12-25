package com.example.LoginPlusSecurity.Seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.LoginPlusSecurity.model.Category;
import com.example.LoginPlusSecurity.repository.CategoryRepository;

@Component
public class CategoryDataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategoryDataLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Technology"));
            categoryRepository.save(new Category("Mindfulness"));
            categoryRepository.save(new Category("News"));
            categoryRepository.save(new Category("Education"));
            categoryRepository.save(new Category("Health"));
            categoryRepository.save(new Category("Travel"));
            categoryRepository.save(new Category("Lifestyle"));
            categoryRepository.save(new Category("Adult"));
            categoryRepository.save(new Category("Entertainment"));
            categoryRepository.save(new Category("Sports"));
        }
    }
}