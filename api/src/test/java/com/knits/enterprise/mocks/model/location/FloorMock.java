package com.knits.enterprise.mocks.model.location;

import com.knits.enterprise.model.enums.LocationUsageType;
import com.knits.enterprise.model.location.Floor;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class FloorMock {

    public static Floor shallowFloor(Long id) {
        return Floor.builder()
                .id(id)
                .startDate(LocalDateTime.now())
                .use(LocationUsageType.WAREHOUSE)
                .number(4)
                .workingAreas(new ArrayList<>())
                .building(BuildingMock.shallowBuilding(null))
                .build();
    }
}
