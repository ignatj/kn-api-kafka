package com.knits.enterprise.model.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
public class Country extends AbstractEntity {

    private String iso;
    private String iso2;
    private String iso3;
    private String name;

}
