package com.knits.enterprise.mapper.asset;

import com.knits.enterprise.dto.data.asset.AssetTechSpecDto;
import com.knits.enterprise.mapper.common.EntityMapper;
import com.knits.enterprise.model.asset.AssetTechSpec;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AssetTechSpecMapper extends EntityMapper<AssetTechSpec, AssetTechSpecDto> {

    @Mapping(target = "id", ignore = true)
    AssetTechSpec toEntity(AssetTechSpecDto dto);
}
