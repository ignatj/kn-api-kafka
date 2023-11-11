package com.knits.enterprise.controller.location;

import com.knits.enterprise.dto.data.common.ValidationErrorDto;
import com.knits.enterprise.dto.data.location.BuildingDto;
import com.knits.enterprise.service.location.BuildingService;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Tag(name = "Building", description = "Building endpoints")
@Slf4j
@Validated
public class BuildingController {

    private BuildingService buildingService;

    private static final String ENDPOINT_NAME="/buildings";

    @Operation(summary = "Create new Building")
    @ApiResponse(responseCode = "201", description = "Building is created")
    @ApiResponse(responseCode = "400", description = "Invalid data provided",
            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class)))
    @PostMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    @Validated(BuildingDto.onBuildingCreate.class)
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<BuildingDto> create(@Valid @RequestBody BuildingDto buildingDto) {
        log.debug("Http request to create {} with data: {} ",ENDPOINT_NAME,buildingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(buildingService.create(buildingDto));
    }

    @Operation(summary = "Get all Buildings")
    @ApiResponse(responseCode = "200", description = "Buildings Found")
    @GetMapping(value = ENDPOINT_NAME, produces = {"application/json"})
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<List<BuildingDto>> getAllActive() {
        log.debug("Http request to get all {}",ENDPOINT_NAME);
        return ResponseEntity.ok().body(buildingService.getAllActive());
    }

    @Operation(summary = "Delete building by its id")
    @ApiResponse(responseCode = "204", description = "Building is deleted")
    @DeleteMapping(value =ENDPOINT_NAME+"/{id}")
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Http request to delete {} with Id: {}",ENDPOINT_NAME, id);
        buildingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
