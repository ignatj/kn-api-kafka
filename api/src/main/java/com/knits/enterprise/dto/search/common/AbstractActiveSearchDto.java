package com.knits.enterprise.dto.search.common;

import com.knits.enterprise.model.common.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder=true)
@NoArgsConstructor
public abstract class AbstractActiveSearchDto<T extends AbstractEntity> extends GenericSearchDto<T>{

    @Override
    protected void addFiltersInternal(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> filters) {

        Predicate organizationNamePredicate = criteriaBuilder
                .equal(root.get("active"), true );
        filters.add(organizationNamePredicate);
        activeWithFilters(root,query,criteriaBuilder,filters);
    }

    protected abstract void activeWithFilters(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<Predicate> filters);

}