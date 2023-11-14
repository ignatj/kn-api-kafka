package com.knits.enterprise.mocks.model.location;

import com.knits.enterprise.model.enums.LocationUsageType;
import com.knits.enterprise.model.location.Floor;

import java.time.LocalDateTime;

public class FloorMock {

    public static Floor shallowFloor(Long id) {
        return Floor.builder()
                .id(id)
                .startDate(LocalDateTime.now())
                .use(LocationUsageType.WAREHOUSE)
                .number(4)
                .build();
    }
}
