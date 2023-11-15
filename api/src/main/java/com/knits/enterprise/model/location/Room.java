package com.knits.enterprise.model.location;

import com.knits.enterprise.model.common.AbstractAuditableEntity;
import com.knits.enterprise.model.enums.LocationUsageType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
@Table(name = "room")
public class Room extends AbstractAuditableEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;

    @Column(name = "use")
    @Enumerated(EnumType.STRING)
    private LocationUsageType use;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "surface_m2")
    private Double surfaceM2;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;
}
