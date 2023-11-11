package com.knits.enterprise.dto.data.location;

import com.knits.enterprise.dto.data.common.AbstractActiveDto;
import com.knits.enterprise.dto.data.common.ContactDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDto extends AbstractActiveDto {

    public interface onBuildingCreate{
    }

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotNull(groups = {onBuildingCreate.class})
    private Long locationId;

    private String street;

    private String zipCode;

    private String receptionPhone;

    private String receptionFax;

    @Valid
    private ContactDto securityContactPerson;

    @Valid
    private ContactDto contactPerson;
}
