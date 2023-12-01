package com.knits.enterprise.model.asset;

import com.knits.enterprise.model.common.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@SuperBuilder
@Data
@Table(name = "iot_metric_data")
public class IOTMetricData extends AbstractEntity {

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_instance_id")
    private AssetInstance machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_instance_id")
    private AssetInstance device;

    @Column(name = "status")
    private String status;

    @Column(name = "metric_name")
    private String metricName;

    @Column(name = "metric_json_payload")
    private String metricJsonPayload;
}
