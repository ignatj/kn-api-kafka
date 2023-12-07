package com.knits.enterprise.model.asset;

import com.knits.enterprise.model.common.AbstractAuditableEntity;
import com.knits.enterprise.model.enums.*;
import com.knits.enterprise.model.location.Building;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.model.location.WorkingArea;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
@Table(name = "asset_instance")
public class AssetInstance extends AbstractAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_model_id", nullable = false)
    private AssetModel assetModel;

    @Column(name = "serial_number", nullable = false)
    private String serialNumber;

    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "barcode", nullable = false)
    private String barcode;

    @Column(name = "description")
    private String description;

    @Column(name = "key")
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition")
    private Condition condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "parent_asset_instance_id")
    private AssetInstance parent;

    @OneToMany
    private List<AssetTechSpec> techSpecs;

    @Enumerated(EnumType.STRING)
    @Column(name = "ownership_type")
    private OwnershipType ownershipType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @Column(name = "purchase_order_code")
    private String purchaseOrderCode;

    @Column(name = "depreciable_asset", columnDefinition = "boolean default true")
    private Boolean depreciableAsset;

    @Column(name = "warranty_number", nullable = false)
    private String warrantyNumber;

    @Column(name = "warranty_start_date", nullable = false)
    private LocalDate warrantyStartDate;

    @Column(name = "warranty_end_date", nullable = false)
    private LocalDate warrantyEndDate;

    @Column(name = "service_provider")
    private String serviceProvider;

    @OneToMany
    private List<Document> documents;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "working_area_id")
    private WorkingArea workingArea;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "depreciation_id")
    private DepreciationTemplate depreciationTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warranty_template_id")
    private WarrantyTemplate warrantyTemplate;

    public void setTechSpecs(List<AssetTechSpec> techSpecs) {
        this.techSpecs = techSpecs;
        for (AssetTechSpec techSpec: techSpecs) {
            techSpec.setAssetInstance(this);
        }
    }

    public void setAssetModel(AssetModel assetModel) {
        this.assetModel = assetModel;
        assetModel.getInstances().add(this);
    }

}