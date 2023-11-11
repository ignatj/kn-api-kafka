package com.knits.enterprise.mocks.model.location;

import com.knits.enterprise.model.location.Building;

public class BuildingMock {

    public static Building shallowBuilding(Long id) {
        return Building.builder()
                .id(id)
                .receptionFax("MockFax")
                .receptionPhone("MockPhone")
                .build();
    }
}
