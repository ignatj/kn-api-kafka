package com.knits.enterprise.dto.data.asset;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.common.AbstractAuditableDto;
import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.model.enums.Condition;
import com.knits.enterprise.model.enums.OwnershipType;
import com.knits.enterprise.model.enums.State;
import com.knits.enterprise.model.enums.Type;
import com.knits.enterprise.validators.ConditionalValidation;
import com.knits.enterprise.validators.ValidDateTimeFormat;
import com.knits.enterprise.validators.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ConditionalValidation(
        conditionalProperty = "ownershipType",
        values = {"OWNED"},
        requiredProperties = {"purchaseOrderCode"},
        message = "If Ownership is Owned then Purchase Order Code has to be entered",
        groups = {LocationDto.onLocationCreate.class}
)
public class AssetInstanceDto extends AbstractAuditableDto {

    public interface onAssetInstanceCreate{}

    @NotNull(groups = onAssetInstanceCreate.class)
    private Long assetModelId;

    @NotBlank(groups = onAssetInstanceCreate.class)
    private String serialNumber;

    @NotBlank(groups = onAssetInstanceCreate.class)
    private String tag;

    @NotBlank(groups = onAssetInstanceCreate.class)
    private String barcode;

    private String description;

    private String key;

    @ValidEnum(enumClass = Condition.class)
    @Schema(implementation = Condition.class)
    private String condition;

    @ValidEnum(enumClass = State.class)
    @Schema(implementation = State.class)
    private String state;

    @ValidEnum(enumClass = Type.class)
    @Schema(implementation = Type.class)
    private String type;

    private Long parentAssetInstanceId;

    @Valid
    private List<AssetTechSpecDto> techSpecs;

    @ValidEnum(enumClass = OwnershipType.class)
    @Schema(implementation = OwnershipType.class)
    private String ownershipType;

    private String purchaseOrderCode;

    @NotNull(groups = onAssetInstanceCreate.class)
    private Long vendorId;

    @Schema(defaultValue = "True")
    private Boolean depreciableAsset = true;

    @NotBlank(groups = onAssetInstanceCreate.class)
    private String warrantyNumber;

    @NotNull(groups = onAssetInstanceCreate.class)
    @ValidDateTimeFormat(format = Constants.DATE_FORMAT_DD_MM_YYYY)
    @Schema(format = Constants.DATE_FORMAT_DD_MM_YYYY)
    private String warrantyStartDate;

    @NotNull(groups = onAssetInstanceCreate.class)
    @ValidDateTimeFormat(format = Constants.DATE_FORMAT_DD_MM_YYYY)
    @Schema(format = Constants.DATE_FORMAT_DD_MM_YYYY)
    private String warrantyEndDate;

    private String serviceProvider;

    @NotNull(groups = onAssetInstanceCreate.class)
    private Long locationId;

    private Long buildingId;

    private Long workingAreaId;

    @NotNull(groups = onAssetInstanceCreate.class)
    private Long orderId;
}
