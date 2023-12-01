package com.knits.enterprise.mapper.asset;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.asset.AssetInstanceDto;
import com.knits.enterprise.dto.data.asset.AssetModelDto;
import com.knits.enterprise.mapper.company.AbstractAuditableEntityMapper;
import com.knits.enterprise.model.asset.AssetInstance;
import com.knits.enterprise.model.asset.AssetModel;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",uses = {AssetTechSpecMapper.class},unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AssetInstanceMapper extends AbstractAuditableEntityMapper<AssetInstance, AssetInstanceDto> {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "startDate", target = "startDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS, defaultExpression = "java(LocalDateTime.now())")
    @Mapping(source = "endDate", target = "endDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "techSpecs", ignore = true)
    AssetModel toEntity(AssetModelDto dto);

    @Override
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "techSpecs", ignore = true)
    void partialUpdate(@MappingTarget AssetInstance entity, AssetInstanceDto dto);
}