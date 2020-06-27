package com.hpkarugendo.repositories;

import com.hpkarugendo.models.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Integer> {
    List<Photo> findAllByOrderById();
}
