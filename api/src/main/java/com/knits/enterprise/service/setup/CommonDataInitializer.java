package com.knits.enterprise.service.setup;

import com.knits.enterprise.model.common.Address;
import com.knits.enterprise.model.common.Contact;
import com.knits.enterprise.model.common.Organization;
import com.knits.enterprise.model.security.User;
import com.knits.enterprise.repository.common.AddressRepository;
import com.knits.enterprise.repository.common.ContactRepository;
import com.knits.enterprise.repository.company.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommonDataInitializer {

    private final OrganizationRepository organizationRepository;

    private final AddressRepository addressRepository;

    private final ContactRepository contactRepository;

    public void setup(User adminUser) {
        setupOrganizations(adminUser);
    }


    private void setupOrganizations(User adminUser) {
        List<Organization> organizations = organizationRepository.findAllActive();
        List<Address> addresses = addressRepository.findAllActive();
        List<Contact> contacts = contactRepository.findAllActive();

        int counter = 0;
        int rotation = Math.min(addresses.size(), contacts.size());
        for (Organization organization : organizations) {
            if (counter == rotation) {
                counter = 0;
            }
            Address address = addresses.get(counter);
            address.setActive(true);
            organization.setLegalAddress(address);
            organization.setContactPerson(contacts.get(counter));
            organization.setTaxRegistrationCountry(address.getCountry());
            organization.setCreatedBy(adminUser);
            organizationRepository.save(organization);
            counter++;
        }
        log.info("setupOrganizations Completed.");
    }

}
