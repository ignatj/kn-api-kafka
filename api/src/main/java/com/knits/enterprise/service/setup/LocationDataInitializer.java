package com.knits.enterprise.service.setup;

import com.knits.enterprise.model.common.Address;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.model.security.User;
import com.knits.enterprise.repository.common.AddressRepository;
import com.knits.enterprise.repository.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class LocationDataInitializer {

    private final AddressRepository addressRepository;

    private final LocationRepository locationRepository;

    public void setup(User adminUser) {
        setupLocations(adminUser);
    }

    private void setupLocations(User adminUser) {
        List<Address> addresses = addressRepository.findAllActive();
        List<Location> locations = locationRepository.findAllActive();

        int counter = 0;

        for (Location location : locations) {
            if (counter == addresses.size()) {
                counter = 0;
            }
            location.setAddress(addresses.get(counter));
            location.setCreatedBy(adminUser);
            location.setActive(true);
            locationRepository.save(location);
            counter++;
        }
        log.info("setupLocations Completed.");
    }
}
