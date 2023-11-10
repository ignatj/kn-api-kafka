package com.knits.enterprise.dto.data.common;

import com.knits.enterprise.dto.data.location.LocationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String iso2;
    private String iso3;

    @NotBlank(groups = {LocationDto.onLocationCreate.class})
    private String name;
}
