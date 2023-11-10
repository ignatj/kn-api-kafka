package com.knits.enterprise.dto.data.location;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.common.AddressDto;
import com.knits.enterprise.dto.data.common.AbstractAuditableDto;
import com.knits.enterprise.dto.data.security.UserDto;
import com.knits.enterprise.model.enums.LocationUsageType;
import com.knits.enterprise.model.enums.OwnershipType;
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

@Data
@SuperBuilder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
@ConditionalValidation(
        conditionalProperty = "mapCoordinates",
        values = {"true"},
        requiredProperties = {"latitude", "longitude"},
        message = "Map coordinates should be included",
        groups = {LocationDto.onLocationCreate.class}
)
public class LocationDto extends AbstractAuditableDto {

    public interface onLocationCreate {
    }

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(groups = {onLocationCreate.class})
    private String name;

    private String description;

    @ValidDateTimeFormat(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    @Schema(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    private String startDate;

    @ValidDateTimeFormat(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    @Schema(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    private String endDate;

    @Valid
    @NotNull(groups = {onLocationCreate.class})
    private AddressDto address;

    @ValidEnum(enumClass = OwnershipType.class, groups = {onLocationCreate.class})
    @Schema(implementation = OwnershipType.class)
    private String ownership;

    private boolean mapCoordinates = false;

    private String latitude;

    private String longitude;

    @ValidEnum(enumClass = LocationUsageType.class, groups = {onLocationCreate.class})
    @Schema(implementation = LocationUsageType.class)
    private String use;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UserDto createdBy;
}
