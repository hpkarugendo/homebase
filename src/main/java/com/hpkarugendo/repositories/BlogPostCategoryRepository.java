package com.hpkarugendo.repositories;

import com.hpkarugendo.models.BlogPostCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostCategoryRepository extends CrudRepository<BlogPostCategory, Integer> {
    List<BlogPostCategory> findAllByOrderById();
    List<BlogPostCategory> findAllByOrderByNameAsc();
}
