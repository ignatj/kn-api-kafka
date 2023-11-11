package com.knits.enterprise.mocks.dto.location;

import com.knits.enterprise.dto.data.location.BuildingDto;

public class BuildingDtoMock {

    public static BuildingDto shallowBuildingDto(Long id, Long locationId) {
        return BuildingDto.builder()
                .id(id)
                .locationId(locationId)
                .street("MockStreet")
                .zipCode("MockZip")
                .receptionFax("MockFax")
                .receptionPhone("MockPhone")
                .active(true)
                .build();
    }
}
