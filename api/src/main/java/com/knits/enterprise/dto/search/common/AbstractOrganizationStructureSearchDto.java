package com.knits.enterprise.dto.search.common;

import com.knits.enterprise.model.company.AbstractOrganizationStructure;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder=true)
@NoArgsConstructor
public class AbstractOrganizationStructureSearchDto<T extends AbstractOrganizationStructure> extends AbstractAuditableSearchDto<T> {

    private String name;

    @Override
    protected void auditableWithFilters(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> filters) {
        if(StringUtils.isNotBlank(name)){
            Predicate organizationNamePredicate = criteriaBuilder
                    .equal(root.get("name"), name );
            filters.add(organizationNamePredicate);
        }
    }

}
