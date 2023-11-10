package com.knits.enterprise.mapper.common;

import com.knits.enterprise.dto.data.common.AddressDto;
import com.knits.enterprise.model.common.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = {CountryMapper.class})
public interface AddressMapper extends EntityMapper<Address, AddressDto> {

    @Mapping(source = "zipCode", target = "postalCode")
    @Mapping(target = "country", ignore = true)
    Address toEntity(AddressDto dto);

    @Mapping(source = "postalCode", target = "zipCode")
    AddressDto toDto(Address entity);
}
