package com.knits.enterprise.controller.company;
import com.knits.enterprise.controller.common.GenericController;
import com.knits.enterprise.dto.api.PaginatedResponseDto;
import com.knits.enterprise.dto.data.company.BusinessUnitDto;
import com.knits.enterprise.dto.search.company.BusinessUnitSearchDto;
import com.knits.enterprise.service.company.BusinessUnitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BusinessUnitController extends GenericController {

    @Autowired
    private BusinessUnitService businessUnitService;

    @PostMapping(value = "/business-units", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<BusinessUnitDto> create(@RequestBody BusinessUnitDto businessUnitDto) {
        log.debug("REST request to create BusinessUnit");
        checkCreateRequest(businessUnitDto);
        return ResponseEntity
                .ok()
                .body(businessUnitService.create(businessUnitDto));
    }

    @GetMapping(value = "/business-units/{id}", produces = {"application/json"})
    public ResponseEntity<BusinessUnitDto> findById(@PathVariable(value = "id") final Long id) {
        log.debug("REST request to get BusinessUnit : {}", id);
        BusinessUnitDto businessUnitFound = businessUnitService.findById(id);
        return ResponseEntity
                .ok()
                .body(businessUnitFound);
    }

    @PatchMapping(value = "/business-units", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<BusinessUnitDto> partialUpdate(@RequestBody BusinessUnitDto businessUnitDto) {
        log.debug("REST request to patch BusinessUnit : {}", businessUnitDto);
        BusinessUnitDto businessUnitFound = businessUnitService.partialUpdate(businessUnitDto);
        return ResponseEntity
                .ok()
                .body(businessUnitFound);
    }

    @DeleteMapping(value = "/business-units/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") final Long id) {
        log.debug("REST request to delete BusinessUnit : {}", id);
        businessUnitService.delete(id);
        return ResponseEntity
                .noContent().build();
    }

    @GetMapping(value = "/business-units", produces = {"application/json"})
    public ResponseEntity<PaginatedResponseDto<BusinessUnitDto>> searchBusinessUnits(BusinessUnitSearchDto searchDto) {
        log.debug("Http Get request to Search BusinessUnits with pagination with data : {}",searchDto.toString());
        PaginatedResponseDto<BusinessUnitDto> paginatedResponse = businessUnitService.search(searchDto);
        return ResponseEntity
                .ok()
                .body(paginatedResponse);
    }


}
