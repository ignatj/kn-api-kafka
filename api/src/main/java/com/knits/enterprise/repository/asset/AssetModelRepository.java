package com.knits.enterprise.repository.asset;

import com.knits.enterprise.model.asset.AssetModel;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetModelRepository extends ActiveEntityRepository<AssetModel> {
}