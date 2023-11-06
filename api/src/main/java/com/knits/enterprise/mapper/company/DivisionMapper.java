package com.knits.enterprise.mapper.company;

import com.knits.enterprise.mapper.security.UserMapper;
import com.knits.enterprise.model.company.Division;
import com.knits.enterprise.dto.data.company.DivisionDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {UserMapper.class})
public interface DivisionMapper extends AbstractOrganizationStructureEntityMapper<Division, DivisionDto> {

}
