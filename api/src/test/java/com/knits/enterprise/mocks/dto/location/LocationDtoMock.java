package com.knits.enterprise.mocks.dto.location;

import com.knits.enterprise.dto.data.common.AddressDto;
import com.knits.enterprise.dto.data.common.CountryDto;
import com.knits.enterprise.dto.data.location.LocationDto;

public class LocationDtoMock {

    public static LocationDto shallowLocationDto(Long id){

        return LocationDto.builder()
                .id(id)
                .name("A mock Name")
                .description("MockDescription")
                .startDate("01/01/2022 01:01:01")
                .mapCoordinates(true)
                .address(
                        AddressDto.builder()
                                .street("MockStreet")
                                .zipCode("MockZip")
                                .city("MockCity")
                                .country(
                                        CountryDto.builder()
                                                .name("Argentina")
                                                .build()
                                )
                                .build()
                )
                .latitude("55.3")
                .longitude("43.2")
                .ownership("OUR_PREMISES")
                .use("OFFICE")
                .active(true)
                .build();
    }

}
