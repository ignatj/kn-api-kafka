package com.knits.enterprise.controller.location;

import com.knits.enterprise.dto.data.common.ValidationErrorDto;
import com.knits.enterprise.dto.data.location.RoomDto;
import com.knits.enterprise.service.location.RoomService;
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
@Tag(name = "Room", description = "Room endpoints")
@Slf4j
public class RoomController {

    private RoomService roomService;

    private static final String ENDPOINT_NAME="/rooms";

    @Operation(summary = "Create new Room")
    @ApiResponse(responseCode = "201", description = "Room is created")
    @ApiResponse(responseCode = "400", description = "Invalid data provided",
            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class)))
    @PostMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<RoomDto> create(@Valid @RequestBody RoomDto roomDto) {
        log.debug("Http request to create {} with data: {} ", ENDPOINT_NAME, roomDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(roomDto));
    }

    @Operation(summary = "Get all Rooms")
    @ApiResponse(responseCode = "200", description = "Rooms Found")
    @GetMapping(value = ENDPOINT_NAME, produces = {"application/json"})
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<List<RoomDto>> getAllActive() {
        log.debug("Http request to get all {}",ENDPOINT_NAME);
        return ResponseEntity.ok().body(roomService.getAllActive());
    }

    @Operation(summary = "Deactivate Room by its id")
    @ApiResponse(responseCode = "204", description = "Room is deactivated")
    @DeleteMapping(value =ENDPOINT_NAME+"/{id}")
    @PreAuthorize("hasAuthority('FacilityAdmin')")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.debug("Http request to deactivate {} with Id: {}",ENDPOINT_NAME, id);
        roomService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
