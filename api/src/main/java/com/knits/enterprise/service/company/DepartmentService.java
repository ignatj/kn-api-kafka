package com.knits.enterprise.service.company;

import com.knits.enterprise.dto.data.company.DepartmentDto;
import com.knits.enterprise.dto.search.company.DepartmentSearchDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.company.DepartmentMapper;
import com.knits.enterprise.model.company.Department;
import com.knits.enterprise.repository.company.DepartmentRepository;
import com.knits.enterprise.service.common.GenericService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DepartmentService extends GenericService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;

    public DepartmentDto create(DepartmentDto departmentDto) {
        String operationLog = "Request to save Department: %s".formatted(departmentDto.toString());
        logCurrentUser(operationLog);
        String departmentName = departmentDto.getName();
        if (repository.findOneByName(departmentName).isPresent()) {
            throw new UserException("Department with name %s already exists".formatted(departmentName));
        }
        Department department = mapper.toEntity(departmentDto);
        department.setCreatedBy(getCurrentUserAsEntity());
        department.setStartDate(LocalDateTime.now());
        department.setActive(true);
        return mapper.toDto(repository.save(department));
    }

    @Transactional
    public DepartmentDto update(DepartmentDto departmentDto) {
        log.debug("Request to update Department: {}", departmentDto);

        Department department = repository.findById(departmentDto.getId()).orElseThrow(() ->
                new UserException("Department#" + departmentDto.getId() + " not found"));
        mapper.update(department, departmentDto);
        repository.save(department);
        return mapper.toDto(department);
    }

    @Transactional
    public DepartmentDto partialUpdate(DepartmentDto departmentDto) {
        log.debug("Request to update Department: {}", departmentDto);

        Department department = repository.findById(departmentDto.getId()).orElseThrow(() ->
                new UserException("Department#" + departmentDto.getId() + " not found"));

        mapper.partialUpdate(department, departmentDto);
        repository.save(department);
        return mapper.toDto(department);
    }

    public void delete(Long id) {
        log.debug("Delete Department by id : {}", id);
        repository.deleteById(id);
    }

    public Page<DepartmentDto> getActive(DepartmentSearchDto departmentSearchDto) {

        Page<Department> departmentPage = repository.findAll(departmentSearchDto.getSpecification(), departmentSearchDto.getPageable());
        List<DepartmentDto> departmentDtos = mapper.toDtos(departmentPage.getContent());
        return new PageImpl<>(departmentDtos, departmentSearchDto.getPageable(), departmentPage.getTotalElements());
    }

    public Page<DepartmentDto> search(DepartmentSearchDto departmentSearchDto) {

        Page<Department> departmentPage = repository.findAll(departmentSearchDto.getSpecification(), departmentSearchDto.getPageable());
        List<DepartmentDto> departmentDtos = mapper.toDtos(departmentPage.getContent());
        return new PageImpl<>(departmentDtos, departmentSearchDto.getPageable(), departmentPage.getTotalElements());
    }
}
