package com.knits.enterprise.mocks.dto.location;

import com.knits.enterprise.dto.data.location.RoomDto;
import com.knits.enterprise.model.enums.LocationUsageType;


public class RoomDtoMock {

    public static RoomDto shallowRoomDto(Long id, Long floorId) {
        return RoomDto.builder()
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
