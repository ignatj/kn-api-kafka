package com.knits.enterprise.dto.data.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.location.BuildingDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class ContactDto extends AbstractActiveDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(groups = {BuildingDto.onBuildingCreate.class})
    private String firstName;

    @NotBlank(groups = {BuildingDto.onBuildingCreate.class})
    private String lastName;

    @NotNull(groups = {BuildingDto.onBuildingCreate.class})
    @Pattern(regexp = Constants.EMAIL_PATTERN, groups = {BuildingDto.onBuildingCreate.class})
    private String email;

    @NotBlank(groups = {BuildingDto.onBuildingCreate.class})
    private String phoneNumber;
    private String jobTitle;
    private String note;

}
