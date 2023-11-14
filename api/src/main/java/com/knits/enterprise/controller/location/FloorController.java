package com.knits.enterprise.controller.location;

import com.knits.enterprise.dto.data.common.ValidationErrorDto;
import com.knits.enterprise.dto.data.location.BuildingDto;
import com.knits.enterprise.dto.data.location.FloorDto;
import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.service.location.FloorService;
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
@Tag(name = "Floor", description = "Floor endpoints")
@Slf4j
public class FloorController {

    private FloorService floorService;

    private static final String ENDPOINT_NAME="/floors";

    @Operation(summary = "Create new Floor")
    @ApiResponse(responseCode = "201", description = "Floor is created")
    @ApiResponse(responseCode = "400", description = "Invalid data provided",
            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class)))
    @PostMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<FloorDto> create(@Valid @RequestBody FloorDto floorDto) {
        log.debug("Http request to create {} with data: {} ", ENDPOINT_NAME, floorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(floorService.create(floorDto));
    }

    @Operation(summary = "Get all Floors")
    @ApiResponse(responseCode = "200", description = "Floors Found")
    @GetMapping(value = ENDPOINT_NAME, produces = {"application/json"})
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<List<FloorDto>> getAllActive() {
        log.debug("Http request to get all {}",ENDPOINT_NAME);
        return ResponseEntity.ok().body(floorService.getAllActive());
    }

    @Operation(summary = "Deactivate floor by its id")
    @ApiResponse(responseCode = "204", description = "Floor is deactivated")
    @DeleteMapping(value =ENDPOINT_NAME+"/{id}")
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.debug("Http request to delete {} with Id: {}",ENDPOINT_NAME, id);
        floorService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

}
