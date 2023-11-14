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

import javax.validation.constraints.NotNull;

@Data
@SuperBuilder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
public class FloorDto extends AbstractAuditableDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull
    private Long buildingId;

    @ValidDateTimeFormat(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    @Schema(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    private String startDate;

    @ValidDateTimeFormat(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    @Schema(format = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    private String endDate;

    @ValidEnum(enumClass = LocationUsageType.class)
    @Schema(implementation = LocationUsageType.class, defaultValue = "Inherited from provided building")
    private String use;

    @NotNull
    private Integer number;

    private Double surfaceM2;

    @Schema(defaultValue = "false")
    private boolean isSecured = false;

    @Schema(defaultValue = "1")
    private Integer gates = 1;

    @Schema(defaultValue = "0")
    private Integer meetingRooms = 0;

    @Schema(defaultValue = "1")
    private Integer toilets = 1;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UserDto createdBy;
}
