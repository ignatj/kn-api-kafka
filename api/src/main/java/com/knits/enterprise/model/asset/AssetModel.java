package com.knits.enterprise.model.asset;

import com.knits.enterprise.model.common.AbstractAuditableEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
@Table(name = "asset_model")
public class AssetModel extends AbstractAuditableEntity {

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "description")
    private String description;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "product_url")
    private String productUrl;

    @Column(name = "photo_url")
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "assetModel")
    private List<AssetTechSpec> techSpecs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_model_id")
    private AssetModel parentModel;

    @OneToMany(mappedBy = "assetModel")
    private List<AssetInstance> instances;

    public void setTechSpecs(List<AssetTechSpec> techSpecs) {
        this.techSpecs = techSpecs;
        for (AssetTechSpec techSpec: techSpecs) {
            techSpec.setAssetModel(this);
        }
    }
}
