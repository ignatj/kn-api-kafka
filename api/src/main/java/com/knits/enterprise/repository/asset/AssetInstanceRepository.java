package com.knits.enterprise.repository.asset;

import com.knits.enterprise.model.asset.AssetInstance;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetInstanceRepository extends ActiveEntityRepository<AssetInstance> {

    @Override
    @Query("select a from AssetInstance a left join fetch a.parent where a.id=:id and a.active = true")
    Optional<AssetInstance> findById(Long id);
}