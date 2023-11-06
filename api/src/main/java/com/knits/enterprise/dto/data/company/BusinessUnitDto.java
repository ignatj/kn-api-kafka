package com.knits.enterprise.dto.data.company;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@ToString
public class BusinessUnitDto extends AbstractOrganizationStructureDto {

}

