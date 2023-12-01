package com.knits.enterprise.model.asset;

import com.knits.enterprise.model.common.AbstractActiveEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
@Table(name = "warranty_template")
public class WarrantyTemplate extends AbstractActiveEntity {
}