package com.knits.enterprise.dto.data.location;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.common.AbstractAuditableDto;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.model.enums.LocationUsageType;
import com.knits.enterprise.validators.ValidDateTimeFormat;
import com.knits.enterprise.validators.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@SuperBuilder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
public class WorkingAreaDto extends AbstractAuditableDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull
    private Long floorId;

    @ValidDateTimeFormat(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    @Schema(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    private String startDate;

    @ValidDateTimeFormat(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    @Schema(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    private String endDate;

    @ValidEnum(enumClass = LocationUsageType.class)
    @Schema(implementation = LocationUsageType.class, defaultValue = "Inherited from provided floor")
    private String use;

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    private Double surfaceM2 = 0d;

    private String picUrl;

    private String latitude;

    private String longitude;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UserDto createdBy;
}
