package com.knits.enterprise.service.setup;

import com.knits.enterprise.model.security.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class EdmDataInitializer {

    private final CommonDataInitializer commonDataInitializer;

    private final SecurityDataInitializer securityInitializer;

    private final LocationDataInitializer locationDataInitializer;

    private final CompanyDataInitializer companyDataInitializer;

    /**
     * This assumes that
     * 1) Database structure has been created already
     * 2) Data in tables have been entered manually or with liquibase
     * 3) Only Connection between tables are missing
     */
    public void setupDatabase() {
        log.info("setupDatabase Started. This might take some minutes, please wait....");

        if (!securityInitializer.checkAdminExists()) {
            User adminUser = securityInitializer.createAdminUser();
            commonDataInitializer.setup(adminUser);
            locationDataInitializer.setup(adminUser);
            companyDataInitializer.setup(adminUser);
            log.info("setupDatabase Terminated.");
        }
    }
}
