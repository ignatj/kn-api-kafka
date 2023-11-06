package com.knits.enterprise.service.dashboard;

import com.knits.enterprise.dto.projection.AggregationResultDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.repository.company.EmployeeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class EmployeeKpiService {
    private final EmployeeRepository employeeRepository;

    public List<AggregationResultDto> countEmployeeByGender() {
        return employeeRepository.countByGender();
    }
    public List<AggregationResultDto> countEmployeeByBusinessUnit(){
        return employeeRepository.countByBusinessUnit();
    }
    public List<AggregationResultDto> countEmployeeByDepartment() {
        return employeeRepository.countByDepartment();
    }
    public List<AggregationResultDto> countEmployeeByJobTitle (){
        return employeeRepository.countByJobTitle();
    }
    public List<AggregationResultDto> countEmployeeByCountry(){
        return employeeRepository.countByCountry();
    }

    public List<AggregationResultDto> countEmployeeBySeniority(){
        return employeeRepository.countBySeniority();
    }

    public List<AggregationResultDto> countEmployeeByAge(){
        return employeeRepository.countByAge();
    }

    public List<AggregationResultDto> countHiresByYears(int yearsAgo){
        return employeeRepository.hiresByLastYears(LocalDateTime.now().minusYears(yearsAgo).getYear());
    }

    public List<AggregationResultDto> countLeavesByYears(int yearsAgo){
        return employeeRepository.leavesByLastYears(LocalDateTime.now().minusYears(yearsAgo).getYear());
    }

    public AggregationResultDto headCountForYear(int year){
        return employeeRepository.headCountByYear(year);
    }

    public List<AggregationResultDto> headCountByYears(int yearsAgo){
        if (yearsAgo>15) throw new UserException("Max supported time is 15 years");
        int [] years =extractsYearsFromNow(yearsAgo);
        List<AggregationResultDto> result = new ArrayList<>();
        for (int year : years){
            result.add(headCountForYear(year));
        }
        return result;
    }


    private int[] extractsYearsFromNow (int yearsAgo){
        int currentYear =LocalDateTime.now().getYear();
        int yearsToFetch = LocalDateTime.now().getYear()-yearsAgo;
        int [] years =new int [yearsAgo];

        for (int i=0; i<yearsAgo; i++){
            years[i]=yearsToFetch+i;
        }
        return years;
    }
}
