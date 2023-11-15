package com.knits.enterprise.repository.location;

import com.knits.enterprise.model.location.Room;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends ActiveEntityRepository<Room> {
}
