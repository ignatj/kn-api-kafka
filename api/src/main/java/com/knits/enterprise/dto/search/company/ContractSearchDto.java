package com.knits.enterprise.dto.search.company;

import com.knits.enterprise.dto.search.common.GenericSearchDto;
import com.knits.enterprise.model.company.Contract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder=true)
@NoArgsConstructor
@AllArgsConstructor
public class ContractSearchDto extends GenericSearchDto<Contract> {

    private String employeeLastName;
    private String fromDate;
    private String toDate;
    private String fileName;
    private String fileType;


    @Override
    protected void addFiltersInternal(Root<Contract> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> filters) {

        if (Strings.isNotBlank(employeeLastName)) {
                Predicate lastNameAsPredicate = criteriaBuilder.like(root.get("employee").get("lastName"), "%" + employeeLastName + "%");
                filters.add(lastNameAsPredicate);
            }
            if (Strings.isNotBlank(fromDate)) {
                LocalDateTime from = LocalDate.parse(fromDate, ofPattern("yyyy-MM-dd")).atStartOfDay();
                Predicate fromDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), from);
                filters.add(fromDatePredicate);
            }
            if (Strings.isNotBlank(toDate)) {
                LocalDateTime to = LocalDate.parse(toDate, ofPattern("yyyy-MM-dd")).atStartOfDay().plusDays(1);
                Predicate toDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), to);
                filters.add(toDatePredicate);
            }
            if (Strings.isNotBlank(fileName)) {
                Predicate fileNameAsPredicate = criteriaBuilder.like(root.get("binaryData").get("title"), "%" + fileName + "%");
                filters.add(fileNameAsPredicate);
            }
            if (Strings.isNotBlank(fileType)) {
                Predicate fileTypeAsPredicate = criteriaBuilder.equal(root.get("binaryData").get("contentType"), "%" + fileType + "%");
                filters.add(fileTypeAsPredicate);
            }
        }

}
