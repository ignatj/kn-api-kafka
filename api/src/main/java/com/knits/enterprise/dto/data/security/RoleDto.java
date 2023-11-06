package com.knits.enterprise.dto.data.security;

import com.knits.enterprise.dto.data.common.AbstractActiveDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
public class RoleDto extends AbstractActiveDto {

    private String name;
}
