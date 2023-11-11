package com.knits.enterprise.model.location;

import com.knits.enterprise.model.common.AbstractActiveEntity;
import com.knits.enterprise.model.common.Contact;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
@Table(name = "building")
public class Building extends AbstractActiveEntity {

    @Column(name = "reception_phone")
    private String receptionPhone;

    @Column(name = "reception_fax")
    private String receptionFax;

    @ManyToOne
    @JoinColumn(name = "security_contact_id")
    private Contact securityContactPerson;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contactPerson;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    public void addLocation(Location location) {
        location.getBuildings().add(this);
        this.location = location;
    }
}
