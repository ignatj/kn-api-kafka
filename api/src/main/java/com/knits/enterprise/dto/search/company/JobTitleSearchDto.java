package com.knits.enterprise.dto.search.company;

import com.knits.enterprise.dto.search.common.AbstractOrganizationStructureSearchDto;
import com.knits.enterprise.dto.search.common.GenericSearchDto;
import com.knits.enterprise.model.company.JobTitle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder=true)
@NoArgsConstructor
public class JobTitleSearchDto extends AbstractOrganizationStructureSearchDto<JobTitle> {


}
