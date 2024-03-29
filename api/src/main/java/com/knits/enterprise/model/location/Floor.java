package com.knits.enterprise.model.location;

import com.knits.enterprise.model.common.AbstractAuditableEntity;
import com.knits.enterprise.model.enums.LocationUsageType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
@Data
@Table(name = "floor")
public class Floor extends AbstractAuditableEntity {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Column(name = "use")
    @Enumerated(EnumType.STRING)
    private LocationUsageType use;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "surface_m2")
    private Double surfaceM2;

    @Column(name = "is_secured")
    private boolean isSecured;

    @Column(name = "gates")
    private Integer gates;

    @Column(name = "meeting_rooms")
    private Integer meetingRooms;

    @Column(name = "toilets")
    private Integer toilets;

    @OneToMany(mappedBy = "floor")
    private List<WorkingArea> workingAreas;

    @OneToMany(mappedBy = "floor")
    private List<Room> rooms;

    public void addWorkingArea(WorkingArea workingArea) {
        workingAreas.add(workingArea);
        workingArea.setFloor(this);
    }

    public void addRoom(Room room) {
        rooms.add(room);
        room.setFloor(this);
    }
}
