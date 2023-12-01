package com.knits.enterprise.repository.asset;

import com.knits.enterprise.model.asset.AssetTechSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetTechSpecRepository extends JpaRepository<AssetTechSpec, Long> {
}
