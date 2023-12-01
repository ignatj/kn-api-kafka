package com.knits.enterprise.mapper.asset;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.asset.AssetModelDto;
import com.knits.enterprise.mapper.company.AbstractAuditableEntityMapper;
import com.knits.enterprise.model.asset.AssetModel;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AssetModelMapper extends AbstractAuditableEntityMapper<AssetModel, AssetModelDto> {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "startDate", target = "startDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS, defaultExpression = "java(LocalDateTime.now())")
    @Mapping(source = "endDate", target = "endDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "parentModel", ignore = true)
    @Mapping(target = "instances", ignore = true)
    @Mapping(target = "techSpecs", ignore = true)
    AssetModel toEntity(AssetModelDto dto);

    @Override
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "parentModel", ignore = true)
    @Mapping(target = "instances", ignore = true)
    @Mapping(target = "techSpecs", ignore = true)
    void partialUpdate(@MappingTarget AssetModel entity, AssetModelDto dto);
}