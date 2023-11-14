package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.FloorDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.location.FloorMapper;
import com.knits.enterprise.model.enums.LocationUsageType;
import com.knits.enterprise.model.location.Building;
import com.knits.enterprise.model.location.Floor;
import com.knits.enterprise.repository.location.BuildingRepository;
import com.knits.enterprise.repository.location.FloorRepository;
import com.knits.enterprise.service.common.GenericService;
import com.knits.enterprise.service.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class FloorService extends GenericService {

    private final FloorRepository floorRepository;
    private final BuildingRepository buildingRepository;
    private final FloorMapper floorMapper;

    public FloorService(FloorRepository floorRepository, BuildingRepository buildingRepository, FloorMapper floorMapper, UserService userService) {
        super(userService);
        this.floorRepository = floorRepository;
        this.buildingRepository = buildingRepository;
        this.floorMapper = floorMapper;
    }


    public FloorDto create(FloorDto floorDto) {
        String operationLog ="Request to create floor : %s".formatted(floorDto.toString());
        logCurrentUser(operationLog);

        Building building = buildingRepository.findById(floorDto.getBuildingId())
                .orElseThrow(() -> new UserException("Building with provided id not found"));

        Floor floor = floorMapper.toEntity(floorDto);

        floor.setCreatedBy(getCurrentUserAsEntity());

        building.addFloor(floor);

        floor.setUse(building.getLocation().getUse());
        if (floorDto.getUse() != null) {
            floor.setUse(LocationUsageType.valueOf(floorDto.getUse()));
        }

        return floorMapper.toDto(floorRepository.save(floor));
    }

    public List<FloorDto> getAllActive() {
        return floorMapper.toDtos(floorRepository.findAllActive());
    }

    public void deactivate(Long id) {
        log.debug("Set status deactivated = true to Floor with Id: {}", id);
        floorRepository.deleteById(id);
    }
}
