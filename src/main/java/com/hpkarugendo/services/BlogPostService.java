package com.hpkarugendo.services;

import com.hpkarugendo.models.BlogPost;
import com.hpkarugendo.models.BlogPostCategory;
import com.hpkarugendo.repositories.BlogPostCategoryRepository;
import com.hpkarugendo.repositories.BlogPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    private BlogPostRepository postRepository;
    private BlogPostCategoryRepository categoryRepository;

    public BlogPostService(BlogPostRepository postRepository, BlogPostCategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    public boolean savePost(BlogPost post){
        boolean ans = true;

        try {
            postRepository.save(post);
        } catch (Exception e) {
            ans = false;
            e.printStackTrace();
        }

        return ans;
    }

    public boolean saveCategory(BlogPostCategory category){
        boolean ans = true;

        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            ans = false;
        }

        return ans;
    }

    public List<BlogPost> listTop5Posts(){
        return postRepository.findTop5ByOrderByDateDesc();
    }

    public List<BlogPost> listPosts(){
        return postRepository.findAllByOrderByIdDesc();
    }

    public List<BlogPostCategory> listCategories(){
        return categoryRepository.findAllByOrderByNameAsc();
    }

    public void deletePost(int id){
        Optional<BlogPost> po = postRepository.findById(id);
        if(po.isPresent()){
            postRepository.delete(po.get());
        }
    }

    public void deleteCategory(int id){
        Optional<BlogPostCategory> co = categoryRepository.findById(id);
        if(co.isPresent()){
            categoryRepository.delete(co.get());
        }
    }

    public BlogPostCategory findCatById(int id){
        Optional<BlogPostCategory> co = categoryRepository.findById(id);
        BlogPostCategory ans = co.get();

        return ans;
    }

    public BlogPost findPostById(int id){
        Optional<BlogPost> bo = postRepository.findById(id);
        BlogPost ans = null;

        if(bo.isPresent()){
            ans = bo.get();
        }

        return ans;
    }

    public void saveAll(List<BlogPostCategory> cats) {
        categoryRepository.saveAll(cats);
    }
}
