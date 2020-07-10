package com.hpkarugendo.repositories;

import com.hpkarugendo.models.Gallery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GalleryRepository extends CrudRepository<Gallery, String> {
    List<Gallery> findAllByOrderById();
    List<Gallery> findTop5ByOrderByIdDesc();
    Optional<Gallery> findByName(String name);
}
