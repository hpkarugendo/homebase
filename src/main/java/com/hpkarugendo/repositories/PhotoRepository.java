package com.hpkarugendo.repositories;

import com.hpkarugendo.models.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, String> {
    List<Photo> findAllByOrderById();
    List<Photo> findTop5ByOrderByIdDesc();
}
