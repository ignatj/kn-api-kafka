package com.knits.enterprise.dto.data.common;

import com.knits.enterprise.dto.data.common.AbstractActiveDto;
import com.knits.enterprise.dto.data.security.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
public class AbstractAuditableDto extends AbstractActiveDto {
    private String startDate;
    private String endDate;
    private UserDto createdBy;
}
