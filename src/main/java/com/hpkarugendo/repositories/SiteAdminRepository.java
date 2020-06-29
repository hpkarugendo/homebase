package com.hpkarugendo.repositories;

import com.hpkarugendo.models.SiteAdmin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteAdminRepository extends CrudRepository<SiteAdmin, String> {
    Optional<SiteAdmin> findByAdminUsername(String username);
}
