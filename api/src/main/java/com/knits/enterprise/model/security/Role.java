package com.knits.enterprise.model.security;

import com.knits.enterprise.model.common.AbstractActiveEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Entity
@Data
public class Role extends AbstractActiveEntity {

    private String name;

}
