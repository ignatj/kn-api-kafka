package com.knits.enterprise.service.location;

import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.dto.search.location.LocationSearchDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.common.AddressMapper;
import com.knits.enterprise.mapper.common.AddressMapperImpl;
import com.knits.enterprise.mapper.common.CountryMapper;
import com.knits.enterprise.mapper.common.CountryMapperImpl;
import com.knits.enterprise.mapper.location.LocationMapper;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.repository.location.LocationRepository;
import com.knits.enterprise.service.common.GenericService;
import com.knits.enterprise.service.security.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class LocationService extends GenericService {

    private final LocationRepository repository;

    private final LocationMapper locationMapper;

    public LocationService(UserService userService, LocationRepository repository, LocationMapper locationMapper) {
        super(userService);
        this.repository = repository;
        this.locationMapper = locationMapper;
    }

    public LocationDto create(LocationDto locationDto) {
        String operationLog ="Request to create Location : %s".formatted(locationDto.toString());
        logCurrentUser(operationLog);

        String locationName =locationDto.getName();
        if (repository.findOneByName(locationName).isPresent()){
            throw new UserException("Location with name %s already exists".formatted(locationName));
        }

        Location location = locationMapper.toEntity(locationDto);
        location.setCreatedBy(getCurrentUserAsEntity());
        location.setStartDate(LocalDateTime.now());
        location.setActive(true);
        return locationMapper.toDto( repository.save(location));
    }

    public LocationDto update(LocationDto locationDto){
        log.debug("Request to edit Location : {}", locationDto);
        final Location locationFromDb = repository.findById(locationDto.getId()).get();
        if ( locationFromDb.getId()==null) {
            String message = "Location with id = " + locationDto.getId() + " does not exist.";
            log.warn(message);
            throw new UserException(message);
        }
        locationMapper.update(locationFromDb,locationDto);
        repository.save(locationFromDb);
        return locationMapper.toDto(locationFromDb);
    }

    public LocationDto partialUpdate (LocationDto locationDto){
        log.debug("Request to partial update Location : {}", locationDto);
        Location location = repository.findById(locationDto.getId()).orElseThrow(() -> new UserException("Location#" + locationDto.getId() + " not found"));
        locationMapper.partialUpdate(location,locationDto);
        repository.save(location);
        return locationMapper.toDto(location);
    }

    public void delete(Long id){
        log.debug("Set status deleted = true to Location Id: {}", id);
        repository.deleteById(id);}

    public Page<LocationDto> search(LocationSearchDto locationSearch) {
        Page<Location> locationPage = repository.findAll(locationSearch.getSpecification(), locationSearch.getPageable());
        List<LocationDto> locationDtos = locationMapper.toDtos(locationPage.getContent());
        return new PageImpl<>(locationDtos, locationSearch.getPageable(), locationPage.getTotalElements());
    }



}
