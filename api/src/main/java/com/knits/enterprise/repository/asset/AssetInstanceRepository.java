package com.knits.enterprise.repository.asset;

import com.knits.enterprise.model.asset.AssetInstance;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetInstanceRepository extends ActiveEntityRepository<AssetInstance> {
}