package com.knits.enterprise.controller.location;

import com.knits.enterprise.dto.data.common.ValidationErrorDto;
import com.knits.enterprise.dto.data.location.WorkingAreaDto;
import com.knits.enterprise.service.location.WorkingAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Tag(name = "WorkingArea", description = "WorkingArea endpoints")
@Slf4j
public class WorkingAreaController {

    private WorkingAreaService workingAreaService;

    private static final String ENDPOINT_NAME="/working_areas";

    @Operation(summary = "Create new Working Area")
    @ApiResponse(responseCode = "201", description = "Working Area is created")
    @ApiResponse(responseCode = "400", description = "Invalid data provided",
            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class)))
    @PostMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<WorkingAreaDto> create(@Valid @RequestBody WorkingAreaDto workingAreaDto) {
        log.debug("Http request to create {} with data: {} ", ENDPOINT_NAME, workingAreaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(workingAreaService.create(workingAreaDto));
    }

    @Operation(summary = "Get all Working Areas")
    @ApiResponse(responseCode = "200", description = "Working Areas Found")
    @GetMapping(value = ENDPOINT_NAME, produces = {"application/json"})
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<List<WorkingAreaDto>> getAllActive() {
        log.debug("Http request to get all {}",ENDPOINT_NAME);
        return ResponseEntity.ok().body(workingAreaService.getAllActive());
    }

    @Operation(summary = "Deactivate Working Area by its id")
    @ApiResponse(responseCode = "204", description = "Working Area is deactivated")
    @DeleteMapping(value =ENDPOINT_NAME+"/{id}")
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.debug("Http request to deactivate {} with Id: {}",ENDPOINT_NAME, id);
        workingAreaService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
