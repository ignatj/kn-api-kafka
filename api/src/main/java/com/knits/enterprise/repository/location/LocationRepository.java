package com.knits.enterprise.repository.location;

import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends ActiveEntityRepository<Location> {

    Optional<Location> findOneByName(String locationName);

    @Override
    @Query("select l from Location l left join fetch l.address where l.id=:id and l.active = true")
    Optional<Location> findById(Long id);
}
