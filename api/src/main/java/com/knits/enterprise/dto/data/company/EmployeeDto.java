package com.knits.enterprise.dto.data.company;

import com.knits.enterprise.dto.data.common.AbstractActiveDto;
import com.knits.enterprise.dto.data.common.OrganizationDto;
import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.model.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
public class EmployeeDto extends AbstractActiveDto {

    private String firstName;
    private String lastName;
    private String email;
    private String birthDate;
    private Gender gender;
    private String startDate;
    private String endDate;
    private String companyPhone;
    private String companyMobileNumber;
    private String role;
    private OrganizationDto organization;
    private LocationDto office;
    private BusinessUnitDto businessUnit;
    private JobTitleDto jobTitle;
    private DepartmentDto department;
    private DivisionDto division;
    private List<GroupDto> groups;
    private TeamDto team;
    private CostCenterDto costCenter;
    private EmployeeDto solidLineManager;
}