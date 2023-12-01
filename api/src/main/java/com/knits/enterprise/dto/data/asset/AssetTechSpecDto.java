package com.knits.enterprise.dto.data.asset;

import com.knits.enterprise.dto.data.common.AbstractActiveDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetTechSpecDto extends AbstractActiveDto {

    @NotBlank
    private String name;

    @NotBlank
    private String value;
}
