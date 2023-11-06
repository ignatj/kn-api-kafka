package com.knits.enterprise.mapper.company;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.common.AbstractAuditableDto;
import com.knits.enterprise.dto.data.company.AbstractOrganizationStructureDto;
import com.knits.enterprise.mapper.common.EntityMapper;
import com.knits.enterprise.model.common.AbstractAuditableEntity;
import com.knits.enterprise.model.company.AbstractOrganizationStructure;
import org.mapstruct.*;

public interface AbstractOrganizationStructureEntityMapper<E extends AbstractOrganizationStructure, D extends AbstractOrganizationStructureDto> extends  EntityMapper<E,D>{

    @Mapping(source = "startDate", target = "startDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    @Mapping(source = "endDate", target = "endDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "employees", ignore = true)
    D toDto(E entity);

    @Mapping(source = "startDate", target = "startDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    @Mapping(source = "endDate", target = "endDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "createdBy", ignore = true)
    E toEntity(D dto);

    @Named("partialUpdateAbstractOrganizationStructure")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "startDate", target = "startDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    @Mapping(source = "endDate", target = "endDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "createdBy", ignore = true)
    void partialUpdate(@MappingTarget E entity, D dto);

    @Named("updateAbstractOrganizationStructure")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(source = "startDate", target = "startDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS)
    @Mapping(source = "endDate", target = "endDate", dateFormat = Constants.TIME_FORMAT_DD_MM_YYYY_HH_MM_SS,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "createdBy", ignore = true)
    void update(@MappingTarget E entity, D dto);
}
