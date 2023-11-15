package com.knits.enterprise.mocks.dto.location;

import com.knits.enterprise.dto.data.location.WorkingAreaDto;
import com.knits.enterprise.model.enums.LocationUsageType;

public class WorkingAreaDtoMock {

    public static WorkingAreaDto shallowWorkingAreaDto(Long id, Long floorId) {
        return WorkingAreaDto.builder()
                .id(id)
                .floorId(floorId)
                .startDate("01/01/2022 01:01:01")
                .use(String.valueOf(LocationUsageType.OFFICE))
                .name("MockName")
                .code("MockCode")
                .latitude("4234234")
                .longitude("534535")
                .active(true)
                .build();
    }
}
