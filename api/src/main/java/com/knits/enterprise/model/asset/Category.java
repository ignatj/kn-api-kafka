package com.knits.enterprise.model.asset;

import com.knits.enterprise.model.common.AbstractActiveEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
@Table(name = "category")
public class Category extends AbstractActiveEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> subcategories;
}
