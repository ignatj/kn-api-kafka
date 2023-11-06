package com.knits.enterprise.dto.data.company;


import com.knits.enterprise.dto.data.common.AbstractAuditableDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
public class OfficeDto extends AbstractAuditableDto {

    private String name;
    private String telephone;
    private String email;
}

