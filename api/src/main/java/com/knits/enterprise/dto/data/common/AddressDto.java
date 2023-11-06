package com.knits.enterprise.dto.data.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto extends AbstractActiveDto {

    private CountryDto country;
    private String city;
    private String street;
    private String zipCode;
}
