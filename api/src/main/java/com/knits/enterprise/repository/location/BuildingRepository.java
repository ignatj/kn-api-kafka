package com.knits.enterprise.repository.location;

import com.knits.enterprise.model.location.Building;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildingRepository extends ActiveEntityRepository<Building> {

    @Override
    @Query("select b from Building b left join fetch b.location where b.id=:id and b.active = true")
    Optional<Building> findById(Long id);
}
