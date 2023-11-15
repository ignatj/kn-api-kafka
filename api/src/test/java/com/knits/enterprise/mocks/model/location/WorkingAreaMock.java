package com.knits.enterprise.mocks.model.location;

import com.knits.enterprise.model.enums.LocationUsageType;
import com.knits.enterprise.model.location.WorkingArea;

import java.time.LocalDateTime;

public class WorkingAreaMock {

    public static WorkingArea shallowWorkingArea(Long id) {
        return WorkingArea.builder()
                .id(id)
                .startDate(LocalDateTime.now())
                .use(LocationUsageType.OFFICE)
                .name("MockName")
                .code("MockCode")
                .latitude("4234234")
                .longitude("534535")
                .floor(FloorMock.shallowFloor(null))
                .build();
    }
}
