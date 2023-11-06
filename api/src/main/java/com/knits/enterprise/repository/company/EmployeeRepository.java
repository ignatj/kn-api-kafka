package com.knits.enterprise.repository.company;

import com.knits.enterprise.dto.projection.AggregationResultDto;
import com.knits.enterprise.model.company.Employee;
import com.knits.enterprise.repository.common.ActiveEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends ActiveEntityRepository<Employee> {

    @Query("select e from Employee e where e.id IN (:ids) AND e.active = true")
    Set<Employee> findAllByIdAsSet(@Param("ids") Set<Long> ids);

    static final String DOUBLE_COLON="\\:\\:";
    @Query("select e.gender as key, count (e.id) as value from Employee e group by e.gender order by value asc")
    List<AggregationResultDto> countByGender();


    @Query("select j.name as key, count (e.id) as value from Employee e join JobTitle j on e.jobTitle.id = j.id group by j.name order by value asc")
    List<AggregationResultDto> countByJobTitle();

    @Query("select d.name as key, count (e.id) as value from Employee e join Department d on e.department.id = d.id group by d.name order by value asc")
    List<AggregationResultDto> countByDepartment();

    @Query("""
            select c.name as key, count (e.id) as value from Employee e join Location l on e.office.id = l.id\
             join Address a on l.address.id=a.id join Country c on a.country.id=c.id group by c.name order by value asc\
            """)
    List<AggregationResultDto> countByCountry();

    @Query("""
            select b.name as key, count (e.id) as value from Employee e \
            join BusinessUnit b on e.businessUnit.id = b.id group by b.name order by value asc\
            """)
    List<AggregationResultDto> countByBusinessUnit();



    /*
        Currently no JPQL function can count difference in days.
        Found this solution using native queries.
        Colons needs to be escaped because of jpql params prefix
    */
    @Query(nativeQuery = true, value = """
            SELECT \
                    case
                        when EXTRACT (DAY from (CURRENT_DATE"+DOUBLE_COLON+"timestamp - e.start_date"+DOUBLE_COLON+"timestamp)) between 0 and 365 then '0' \
                        when EXTRACT (DAY from (CURRENT_DATE"+DOUBLE_COLON+"timestamp - e.start_date"+DOUBLE_COLON+"timestamp)) between 366 and 730 then '1' \
                        when EXTRACT (DAY from (CURRENT_DATE"+DOUBLE_COLON+"timestamp - e.start_date"+DOUBLE_COLON+"timestamp)) between 731 and 1095 then '2' \
                        when EXTRACT (DAY from (CURRENT_DATE"+DOUBLE_COLON+"timestamp - e.start_date"+DOUBLE_COLON+"timestamp)) between 1096 and 1825 then '5+' \
                        else '10+' \
                    end as key, \
                count (e.id) as value\
             from employee e  \
             group by key \
            """
            )
    List<AggregationResultDto> countBySeniority();

    @Query("""
            select case \
                when (extract(year from CURRENT_DATE) - extract(year from e.birthDate)) between 20 and 24 then '20-24 years'\
                when (extract(year from CURRENT_DATE) - extract(year from e.birthDate)) between 25 and 29 then '25-29 years'\
                when (extract(year from CURRENT_DATE) - extract(year from e.birthDate)) between 30 and 34 then '30-34 years'\
                when (extract(year from CURRENT_DATE) - extract(year from e.birthDate)) between 35 and 39 then '35-39 years'\
                else '40+ years'\
              end as key,\
              count(e.id) as value \
            from Employee e \
            group by key\
            """)
    List<AggregationResultDto> countByAge();


    @Query("""
            select \
            extract(year from e.startDate) as key, \
            count (e.id) as value \
            from Employee e \
            where extract(year from e.startDate) >= :timeFrame \
            group by key \
            """)
    List<AggregationResultDto> hiresByLastYears(@Param("timeFrame") Integer timeFrame);

    @Query("""
        select \
        extract(year from e.endDate) as key, \
        count (e.id) as value \
        from Employee e \
        where extract(year from e.endDate) >= :timeFrame \
        group by key \
        """)
    List<AggregationResultDto> leavesByLastYears(@Param("timeFrame") Integer timeFrame);


    @Query("""
            select \
            concat ('',:yearToReport) as key, \
            count (e) as value \
            from Employee e \
            where \
             extract (year from e.startDate) < :yearToReport \
             AND \
             (e.endDate IS NULL OR extract(year from e.endDate) >= :yearToReport )\
            """)
    AggregationResultDto headCountByYear(@Param("yearToReport") Integer yearToReport);


}
