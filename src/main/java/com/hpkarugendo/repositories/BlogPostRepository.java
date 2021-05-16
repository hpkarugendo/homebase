package com.hpkarugendo.repositories;

import com.hpkarugendo.models.BlogPost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends CrudRepository<BlogPost, Integer> {
    List<BlogPost> findAllByOrderByIdDesc();
    List<BlogPost> findTop5ByOrderByDateDesc();
}
