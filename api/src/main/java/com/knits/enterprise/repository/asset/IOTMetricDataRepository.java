package com.knits.enterprise.repository.asset;

import com.knits.enterprise.model.asset.IOTMetricData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOTMetricDataRepository extends JpaRepository<IOTMetricData, Long> {
}
