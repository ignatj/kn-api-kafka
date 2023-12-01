package com.knits.enterprise.model.asset;

import com.knits.enterprise.model.common.AbstractActiveEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
@Table(name = "asset_tech_spec")
public class AssetTechSpec extends AbstractActiveEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_model_id")
    private AssetModel assetModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_instance_id")
    private AssetInstance assetInstance;
}
