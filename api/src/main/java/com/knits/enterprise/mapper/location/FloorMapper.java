package com.knits.enterprise.mapper.location;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.location.FloorDto;
import com.knits.enterprise.mapper.company.AbstractAuditableEntityMapper;
import com.knits.enterprise.model.location.Floor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",uses = {BuildingMapper.class},unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FloorMapper extends AbstractAuditableEntityMapper<Floor, FloorDto> {

    @Mapping(target = "building", ignore = true)
    @Mapping(target = "use", ignore = true)
    @Mapping(source = "startDate", target = "startDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS, defaultExpression = "java(LocalDateTime.now())")
    @Mapping(source = "endDate", target = "endDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "createdBy", ignore = true)
    Floor toEntity(FloorDto dto);

    @Mapping(source = "entity.building.id", target = "buildingId")
    @Mapping(target = "use", expression = "java(String.valueOf(entity.getUse()))")
    FloorDto toDto(Floor entity);
}
