package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.RoomDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.location.RoomMapper;
import com.knits.enterprise.model.enums.LocationUsageType;
import com.knits.enterprise.model.location.Floor;
import com.knits.enterprise.model.location.Room;
import com.knits.enterprise.repository.location.FloorRepository;
import com.knits.enterprise.repository.location.RoomRepository;
import com.knits.enterprise.service.common.GenericService;
import com.knits.enterprise.service.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class RoomService extends GenericService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final FloorRepository floorRepository;

    public RoomService(FloorRepository floorRepository, UserService userService, RoomRepository roomRepository, RoomMapper roomMapper) {
        super(userService);
        this.floorRepository = floorRepository;
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public RoomDto create(RoomDto roomDto) {
        String operationLog ="Request to create Room : %s".formatted(roomDto.toString());
        logCurrentUser(operationLog);

        Floor floor = floorRepository.findById(roomDto.getFloorId())
                .orElseThrow(() -> new UserException("Floor with provided id not found"));

        Room room = roomMapper.toEntity(roomDto);

        room.setCreatedBy(getCurrentUserAsEntity());
        floor.addRoom(room);

        room.setUse(floor.getUse());

        if (roomDto.getUse() != null) {
            room.setUse(LocationUsageType.valueOf(roomDto.getUse()));
        }

        return roomMapper.toDto(roomRepository.save(room));
    }

    public List<RoomDto> getAllActive() {
        return roomMapper.toDtos(roomRepository.findAllActive());
    }

    public void delete(Long id) {
        log.debug("Set status deleted = true to Room with Id: {}", id);
        roomRepository.deleteById(id);
    }
}