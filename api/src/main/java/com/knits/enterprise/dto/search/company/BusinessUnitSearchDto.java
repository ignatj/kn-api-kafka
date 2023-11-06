package com.knits.enterprise.dto.search.company;

import com.knits.enterprise.dto.search.common.AbstractOrganizationStructureSearchDto;
import com.knits.enterprise.dto.search.common.GenericSearchDto;
import com.knits.enterprise.model.common.Organization;
import com.knits.enterprise.model.company.BusinessUnit;
import com.knits.enterprise.model.company.Employee;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class BusinessUnitSearchDto extends AbstractOrganizationStructureSearchDto<BusinessUnit> {

}
