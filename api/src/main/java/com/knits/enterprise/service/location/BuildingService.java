package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.BuildingDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.common.ContactPersonMapper;
import com.knits.enterprise.mapper.location.BuildingMapper;
import com.knits.enterprise.model.common.Address;
import com.knits.enterprise.model.common.Contact;
import com.knits.enterprise.model.location.Building;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.repository.common.ContactRepository;
import com.knits.enterprise.repository.location.BuildingRepository;
import com.knits.enterprise.repository.location.LocationRepository;
import com.knits.enterprise.service.common.GenericService;
import com.knits.enterprise.service.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class BuildingService extends GenericService {

    private final BuildingRepository buildingRepository;
    private final LocationRepository locationRepository;
    private final BuildingMapper buildingMapper;
    private final ContactPersonMapper contactPersonMapper;
    private final ContactRepository contactRepository;

    public BuildingService(UserService userService,
                           BuildingRepository buildingRepository,
                           LocationRepository locationRepository,
                           BuildingMapper buildingMapper,
                           ContactPersonMapper contactPersonMapper,
                           ContactRepository contactRepository) {
        super(userService);
        this.buildingRepository = buildingRepository;
        this.locationRepository = locationRepository;
        this.buildingMapper = buildingMapper;
        this.contactPersonMapper = contactPersonMapper;
        this.contactRepository = contactRepository;
    }

    public BuildingDto create(BuildingDto buildingDto) {
        String operationLog ="Request to create building : %s".formatted(buildingDto.toString());
        logCurrentUser(operationLog);

        Location location = locationRepository.findById(buildingDto.getLocationId())
                .orElseThrow(() -> new UserException("Invalid Location id provided"));

        Building building = buildingMapper.toEntity(buildingDto);

        Address address = location.getAddress();

        if (buildingDto.getStreet() != null) {
            address.setStreet(buildingDto.getStreet());
        }
        if (buildingDto.getZipCode() != null) {
            address.setPostalCode(buildingDto.getZipCode());
        }

        building.addLocation(location);

        if (buildingDto.getSecurityContactPerson() != null) {
            Contact contact = contactPersonMapper.toEntity(buildingDto.getSecurityContactPerson());
            contactRepository.save(contact);
            building.setSecurityContactPerson(contact);
        }
        if (buildingDto.getContactPerson() != null) {
            Contact contact = contactPersonMapper.toEntity(buildingDto.getContactPerson());
            contactRepository.save(contact);
            building.setContactPerson(contact);
        }

        return buildingMapper.toDto(buildingRepository.save(building));
    }

    public List<BuildingDto> getAllActive() {
        return buildingMapper.toDtos(buildingRepository.findAllActive());
    }

    public void delete(Long id) {
        log.debug("Set status deleted = true to Building Id: {}", id);
        buildingRepository.deleteById(id);
    }
}
