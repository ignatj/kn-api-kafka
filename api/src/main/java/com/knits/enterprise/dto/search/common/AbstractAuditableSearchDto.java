package com.knits.enterprise.dto.search.common;

import com.knits.enterprise.model.common.AbstractAuditableEntity;
import com.knits.enterprise.model.common.Organization;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder=true)
@NoArgsConstructor
public abstract class AbstractAuditableSearchDto <T extends AbstractAuditableEntity> extends AbstractActiveSearchDto<T>{

    private String startDateFrom;
    private String startDateTo;
    private Long createdById;
    private String username;
    @Override
    protected void activeWithFilters(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> filters) {
        if (createdById != null) {
            Predicate createdBuAsPredicate = criteriaBuilder.equal(root.get("createdBy").get("id"), createdById);
            filters.add(createdBuAsPredicate);
        }

        if (Strings.isNotBlank(startDateFrom)) {
            ZonedDateTime from = ZonedDateTime.parse(startDateFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Predicate startDateAsPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), from);
            filters.add(startDateAsPredicate);
        }

        if (Strings.isNotBlank(startDateTo)) {
            ZonedDateTime to = ZonedDateTime.parse(startDateTo, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Predicate startDateToAsPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("startDateTo"), to);
            filters.add(startDateToAsPredicate);
        }

        if (Strings.isNotBlank(username)) {
            Predicate createdBuAsPredicate = criteriaBuilder.equal(root.get("createdBy").get("username"), username);
            filters.add(createdBuAsPredicate);
        }
        auditableWithFilters(root,query,criteriaBuilder,filters);

    }

    protected abstract void auditableWithFilters (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> filters);

}
