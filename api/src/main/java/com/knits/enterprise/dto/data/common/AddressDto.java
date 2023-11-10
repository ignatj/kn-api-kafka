package com.knits.enterprise.dto.data.common;

import com.knits.enterprise.dto.data.location.LocationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto extends AbstractActiveDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Valid
    @NotNull(groups = {LocationDto.onLocationCreate.class})
    private CountryDto country;

    @NotBlank(groups = {LocationDto.onLocationCreate.class})
    private String city;

    @NotBlank(groups = {LocationDto.onLocationCreate.class})
    private String street;

    @NotBlank(groups = {LocationDto.onLocationCreate.class})
    private String zipCode;

    private boolean active = true;
}
