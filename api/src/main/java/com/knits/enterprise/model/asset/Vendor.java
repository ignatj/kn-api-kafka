package com.knits.enterprise.model.asset;

import com.knits.enterprise.model.common.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
@Table(name = "vendor")
public class Vendor extends AbstractEntity {
}