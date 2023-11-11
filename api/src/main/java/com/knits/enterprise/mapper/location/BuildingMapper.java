package com.knits.enterprise.mapper.location;

import com.knits.enterprise.dto.data.location.BuildingDto;
import com.knits.enterprise.mapper.common.ContactPersonMapper;
import com.knits.enterprise.mapper.common.EntityMapper;
import com.knits.enterprise.model.location.Building;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",uses = {ContactPersonMapper.class},unmappedTargetPolicy = ReportingPolicy.WARN)
public interface BuildingMapper extends EntityMapper<Building, BuildingDto> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "securityContactPerson", ignore = true)
    @Mapping(target = "contactPerson", ignore = true)
    Building toEntity(BuildingDto dto);

    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.address.street", target = "street")
    @Mapping(source = "location.address.postalCode", target = "zipCode")
    BuildingDto toDto(Building entity);
}
