package com.knits.enterprise.mocks.dto.location;

import com.knits.enterprise.dto.data.location.LocationDto;

public class LocationDtoMock {

    public static LocationDto shallowLocationDto(Long id){

        return LocationDto.builder()
                .id(id)
                .name("A mock Name")
                .mapCoordinates(false)
                .latitude("55.3")
                .longitude("43.2")
                .ownership("OUR_PREMISES")
                .realEstate("OFFICE")
                .isDeleted(false)
                .build();
    }

}
