package com.knits.enterprise.mapper.location;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.mapper.common.AddressMapper;
import com.knits.enterprise.mapper.company.AbstractAuditableEntityMapper;
import com.knits.enterprise.model.location.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",uses = {AddressMapper.class},unmappedTargetPolicy = ReportingPolicy.WARN)
public interface LocationMapper extends AbstractAuditableEntityMapper<Location,LocationDto> {

    @Mapping(source = "startDate", target = "startDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS, defaultExpression = "java(LocalDateTime.now())")
    @Mapping(source = "endDate", target = "endDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "latitude", source = "latitude", conditionExpression = "java(dto.isMapCoordinates())")
    @Mapping(target = "longitude", source = "longitude", conditionExpression = "java(dto.isMapCoordinates())")
    @Mapping(target = "createdBy", ignore = true)
    Location toEntity(LocationDto dto);


}

