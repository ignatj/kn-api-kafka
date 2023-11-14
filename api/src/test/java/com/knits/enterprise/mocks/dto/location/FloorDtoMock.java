package com.knits.enterprise.mocks.dto.location;

import com.knits.enterprise.dto.data.location.FloorDto;
import com.knits.enterprise.model.enums.LocationUsageType;


public class FloorDtoMock {

    public static FloorDto shallowFloorDto(Long id, Long buildingId) {
        return FloorDto.builder()
                .id(id)
                .buildingId(buildingId)
                .startDate("01/01/2022 01:01:01")
                .use(String.valueOf(LocationUsageType.OFFICE))
                .number(4)
                .active(true)
                .build();
    }
}
