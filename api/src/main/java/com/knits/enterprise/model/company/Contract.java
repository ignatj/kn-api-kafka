package com.knits.enterprise.model.company;

import com.knits.enterprise.model.common.AbstractActiveEntity;
import com.knits.enterprise.model.common.AbstractAuditableEntity;
import com.knits.enterprise.model.common.BinaryData;
import com.knits.enterprise.model.security.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Table(name = "contract")
public class Contract extends AbstractAuditableEntity {

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "binary_id")
    private BinaryData binaryData;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn (name = "creator_id")
    private User createdBy;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "employee_id")
    private  Employee employee;


}
