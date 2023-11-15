package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.WorkingAreaDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.location.WorkingAreaMapper;
import com.knits.enterprise.model.enums.LocationUsageType;
import com.knits.enterprise.model.location.Floor;
import com.knits.enterprise.model.location.WorkingArea;
import com.knits.enterprise.repository.location.FloorRepository;
import com.knits.enterprise.repository.location.WorkingAreaRepository;
import com.knits.enterprise.service.common.GenericService;
import com.knits.enterprise.service.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class WorkingAreaService extends GenericService {

    private final WorkingAreaRepository workingAreaRepository;
    private final WorkingAreaMapper workingAreaMapper;
    private final FloorRepository floorRepository;

    public WorkingAreaService(WorkingAreaRepository workingAreaRepository, WorkingAreaMapper workingAreaMapper, UserService userService, FloorRepository floorRepository) {
        super(userService);
        this.workingAreaRepository = workingAreaRepository;
        this.workingAreaMapper = workingAreaMapper;
        this.floorRepository = floorRepository;
    }


    public WorkingAreaDto create(WorkingAreaDto workingAreaDto) {
        String operationLog ="Request to create Working Area : %s".formatted(workingAreaDto.toString());
        logCurrentUser(operationLog);

        Floor floor = floorRepository.findById(workingAreaDto.getFloorId())
                .orElseThrow(() -> new UserException("Floor with provided id not found"));

        WorkingArea workingArea = workingAreaMapper.toEntity(workingAreaDto);

        workingArea.setCreatedBy(getCurrentUserAsEntity());

        floor.addWorkingArea(workingArea);

        workingArea.setUse(floor.getUse());
        if (workingAreaDto.getUse() != null) {
            workingArea.setUse(LocationUsageType.valueOf(workingAreaDto.getUse()));
        }

        return workingAreaMapper.toDto(workingAreaRepository.save(workingArea));
    }

    public List<WorkingAreaDto> getAllActive() {
        return workingAreaMapper.toDtos(workingAreaRepository.findAllActive());
    }

    public void delete(Long id) {
        log.debug("Set status deleted = true to Working Area with Id: {}", id);
        workingAreaRepository.deleteById(id);
    }
}
