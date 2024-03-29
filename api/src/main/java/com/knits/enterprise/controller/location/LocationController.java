package com.knits.enterprise.controller.location;

import com.knits.enterprise.dto.data.common.ValidationErrorDto;
import com.knits.enterprise.dto.data.location.LocationDto;
import com.knits.enterprise.dto.search.location.LocationSearchDto;
import com.knits.enterprise.service.location.LocationService;
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
@Tag(name = "Location", description = "Location endpoints")
@Slf4j
@Validated
public class LocationController {

    private LocationService locationService;

    private static final String ENDPOINT_NAME="/locations";

    @Operation(summary = "Create new Location")
    @ApiResponse(responseCode = "201", description = "Location is created")
    @ApiResponse(responseCode = "400", description = "Invalid data provided",
            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class)))
    @PostMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    @Validated(LocationDto.onLocationCreate.class)
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<LocationDto> create(@Valid @RequestBody LocationDto locationDto) {
        log.debug("Http request to create {} with data: {} ",ENDPOINT_NAME,locationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.create(locationDto));
    }

    @Operation(summary = "Edit existing Location")
    @ApiResponse(responseCode = "204", description = "Location was modified")
    @ApiResponse(responseCode = "400", description = "Invalid data provided",
            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class)))
    @PutMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<LocationDto> update(@RequestBody LocationDto locationDto) {
        log.debug("Http request to update {} with data {} ",ENDPOINT_NAME,locationDto);
        locationService.update(locationDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<LocationDto> partialUpdate(@RequestBody LocationDto locationDto) {
        log.debug("Http request to partialUpdate {} with request data: {} ",ENDPOINT_NAME,locationDto);
        return ResponseEntity.ok().body(locationService.partialUpdate(locationDto));
    }

    @DeleteMapping(value =ENDPOINT_NAME+"/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.debug("Http request to deactivate {} with Id: {}",ENDPOINT_NAME, id);
        locationService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<List<LocationDto>> search(@RequestBody LocationSearchDto locationSearchDto) {
        log.debug("Http request to search {} with request data: {} ",ENDPOINT_NAME, locationSearchDto.toString());
        return ResponseEntity.ok().body(locationService.search(locationSearchDto).toList());
    }



}
