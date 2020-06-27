package com.hpkarugendo.repositories;

import com.hpkarugendo.models.Gallery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends CrudRepository<Gallery, Integer> {
    List<Gallery> findAllByOrderById();
}
