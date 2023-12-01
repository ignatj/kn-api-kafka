package com.knits.enterprise.dto.data.asset;

import com.knits.enterprise.dto.data.common.AbstractAuditableDto;
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
public class AssetModelDto extends AbstractAuditableDto {

    public interface onAssetModelCreate{}

    private Long parentAssetModelId;

    @NotBlank(groups = onAssetModelCreate.class)
    private String manufacturer;

    @NotBlank(groups = onAssetModelCreate.class)
    private String description;

    @NotBlank(groups = onAssetModelCreate.class)
    private String model;

    @NotBlank(groups = onAssetModelCreate.class)
    private String code;

    @NotNull(groups = onAssetModelCreate.class)
    private Long categoryId;

    @NotNull(groups = onAssetModelCreate.class)
    private Long subcategory1Id;

    private Long subcategory2Id;

    private String productUrl;

    private String photoUrl;

    @Valid
    private List<AssetTechSpecDto> specDtos;
}
