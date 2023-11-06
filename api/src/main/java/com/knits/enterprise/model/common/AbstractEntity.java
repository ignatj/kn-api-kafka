package com.knits.enterprise.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

@MappedSuperclass
@NoArgsConstructor
@Data
@SuperBuilder(toBuilder=true)
@EqualsAndHashCode
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator",initialValue = 10000,allocationSize = 10)
    private Long id;
}
