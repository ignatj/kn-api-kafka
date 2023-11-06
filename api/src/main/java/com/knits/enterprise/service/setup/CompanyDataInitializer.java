package com.knits.enterprise.service.setup;

import com.knits.enterprise.model.common.Organization;
import com.knits.enterprise.model.company.*;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.model.security.User;
import com.knits.enterprise.repository.company.*;
import com.knits.enterprise.repository.location.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class CompanyDataInitializer {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    private final BusinessUnitRepository businessUnitRepository;

    private final JobTitleRepository jobTitleRepository;

    private final CostCenterRepository costCenterRepository;

    private final DivisionRepository divisionRepository;

    private final GroupRepository groupRepository;

    private final TeamRepository teamRepository;

    private final OrganizationRepository organizationRepository;

    private final LocationRepository locationRepository;

    public void setup(User adminUser) {
        List<Employee> employees = employeeRepository.findAllActive();
        setupBusinessUnits(employees, adminUser);
        setupDivisions(employees, adminUser);
        setupJobTitles(employees, adminUser);
        setupDepartments(employees, adminUser);
        setupCostCenters(employees, adminUser);
        setupTeams(employees, adminUser);
        setupOrganizations(employees, adminUser);
        setupOffices(employees, adminUser);

        setupGroups(employees, adminUser);
    }


    public void setupOrganizations(List<Employee> employees, User admin) {

        List<Organization> organizations = organizationRepository.findAllActive();
        int counter = 0;
        for (Employee employee : employees) {
            if (counter == organizations.size()) {
                counter = 0;
            }
            Organization organization = organizations.get(counter);
            organization.setCreatedBy(admin);
            organization.setActive(true);
            organizationRepository.save(organization);

            employee.setOrganization(organization);
            employeeRepository.save(employee);
            counter++;
        }
        log.info("setupOrganizations Completed.");
    }

    public void setupBusinessUnits(List<Employee> employees, User admin) {

        List<BusinessUnit> businessUnits = businessUnitRepository.findAllActive();
        int bunitCounter = 0;
        for (Employee employee : employees) {
            if (bunitCounter == businessUnits.size()) {
                bunitCounter = 0;
            }
            BusinessUnit businessUnit = businessUnits.get(bunitCounter);
            businessUnit.setActive(true);
            businessUnit.setCreatedBy(admin);
            businessUnitRepository.save(businessUnit);

            employee.setBusinessUnit(businessUnit);
            employeeRepository.save(employee);
            bunitCounter++;

        }
        log.info("setupBusinessUnits Completed.");
    }

    public void setupDivisions(List<Employee> employees, User admin) {

        List<Division> divisions = divisionRepository.findAllActive();
        int counter = 0;
        for (Employee employee : employees) {
            if (counter == divisions.size()) {
                counter = 0;
            }
            Division division = divisions.get(counter);
            division.setCreatedBy(admin);
            division.setActive(true);
            divisionRepository.save(division);

            employee.setDivision(division);
            employeeRepository.save(employee);
            counter++;
        }
        log.info("setupDivisions Completed.");
    }

    public void setupJobTitles(List<Employee> employees, User admin) {

        List<JobTitle> jobTitles = jobTitleRepository.findAllActive();
        int counter = 0;
        for (Employee employee : employees) {
            if (counter == jobTitles.size()) {
                counter = 0;
            }
            JobTitle jobTitle = jobTitles.get(counter);
            jobTitle.setCreatedBy(admin);
            jobTitle.setActive(true);
            jobTitleRepository.save(jobTitle);

            employee.setJobTitle(jobTitle);
            employeeRepository.save(employee);
            counter++;
        }
        log.info("setupJobTitles Completed.");
    }

    public void setupDepartments(List<Employee> employees, User admin) {

        List<Department> departments = departmentRepository.findAllActive();
        int counter = 0;
        for (Employee employee : employees) {
            if (counter == departments.size()) {
                counter = 0;
            }
            Department department = departments.get(counter);
            department.setCreatedBy(admin);
            department.setActive(true);
            departmentRepository.save(department);

            employee.setDepartment(departments.get(counter));
            employeeRepository.save(employee);
            counter++;
        }
        log.info("setupDepartments Completed.");
    }


    public void setupCostCenters(List<Employee> employees, User admin) {

        List<CostCenter> costCenters = costCenterRepository.findAllActive();
        int counter = 0;
        for (Employee employee : employees) {
            if (counter == costCenters.size()) {
                counter = 0;
            }
            CostCenter costCenter = costCenters.get(counter);
            costCenter.setCreatedBy(admin);
            costCenter.setActive(true);
            costCenterRepository.save(costCenter);

            employee.setCostCenter(costCenter);
            employeeRepository.save(employee);
            counter++;
        }
        log.info("setupOrganizations Completed.");
    }

    public void setupTeams(List<Employee> employees, User admin) {

        List<Team> teams = teamRepository.findAllActive();
        int counter = 0;
        for (Employee employee : employees) {
            if (counter == teams.size()) {
                counter = 0;
            }
            Team team = teams.get(counter);
            team.setCreatedBy(admin);
            team.setActive(true);
            teamRepository.save(team);

            employee.setTeam(team);
            employeeRepository.save(employee);
            counter++;
        }
        log.info("setupTeams Completed.");
    }

    public void setupGroups(List<Employee> employees, User admin) {

        List<Group> groups = groupRepository.findAllActive();
        int counter = 0;

        for (Group group : groups) {
            if (counter == employees.size()) {
                counter = 0;
            }

            Set<Employee> employeeSet = new HashSet<>();
            employeeSet.add(employees.get(counter));
            group.setEmployees(employeeSet);
            group.setCreatedBy(admin);
            group.setActive(true);
            groupRepository.save(group);
            counter++;
        }
        log.info("setupGroups Completed.");
    }

    public void setupOffices(List<Employee> employees, User adminUser) {
        List<Location> locations = locationRepository.findAllActive();
        int counter = 0;
        for (Employee employee : employees) {
            if (counter == locations.size()) {
                counter = 0;
            }
            employee.setOffice(locations.get(counter));
            employeeRepository.save(employee);
            counter++;
        }
        log.info("setupOffices Completed.");
    }
}
