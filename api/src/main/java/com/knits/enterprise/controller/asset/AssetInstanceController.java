package com.knits.enterprise.controller.asset;

import com.knits.enterprise.dto.data.asset.AssetInstanceDto;
import com.knits.enterprise.dto.data.common.ValidationErrorDto;
import com.knits.enterprise.service.asset.AssetInstanceService;
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
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Tag(name = "AssetInstance", description = "AssetInstance endpoints")
@Slf4j
@Validated
public class AssetInstanceController {

    private AssetInstanceService assetInstanceService;

    private static final String ENDPOINT_NAME="/asset-instances";

    @Operation(summary = "Create new AssetInstance")
    @ApiResponse(responseCode = "201", description = "AssetInstance is created")
    @ApiResponse(responseCode = "400", description = "Invalid data provided",
            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class)))
    @PostMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    @Validated(AssetInstanceDto.onAssetInstanceCreate.class)
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<AssetInstanceDto> create(@Valid @RequestBody AssetInstanceDto assetInstanceDto) {
        log.debug("Http request to create {} with data: {} ",ENDPOINT_NAME,assetInstanceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assetInstanceService.create(assetInstanceDto));
    }

    @Operation(summary = "Partially update existing AssetInstance")
    @ApiResponse(responseCode = "204", description = "AssetInstance is updated")
    @ApiResponse(responseCode = "400", description = "Invalid data provided",
            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class)))
    @PatchMapping(value = ENDPOINT_NAME + "/{id}", produces = {"application/json"}, consumes = {"application/json"})
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<Void> partialUpdate(@RequestBody AssetInstanceDto assetInstanceDto, @PathVariable Long id) {
        log.debug("Http request to partialUpdate {} with request data: {} ",ENDPOINT_NAME,assetInstanceDto);
        assetInstanceService.partialUpdate(id, assetInstanceDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deactivate existing AssetInstance")
    @ApiResponse(responseCode = "204", description = "AssetInstance is deactivated")
    @ApiResponse(responseCode = "404", description = "AssetInstance not found")
    @DeleteMapping(value =ENDPOINT_NAME+"/{id}")
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.debug("Http request to delete {} with Id: {}",ENDPOINT_NAME, id);
        assetInstanceService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all active AssetInstances")
    @ApiResponse(responseCode = "200", description = "AssetInstance Found")
    @GetMapping(value = ENDPOINT_NAME, produces = {"application/json"})
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<List<AssetInstanceDto>> getAllActive() {
        log.debug("Http request to get all {}",ENDPOINT_NAME);
        return ResponseEntity.ok().body(assetInstanceService.getAllActive());
    }

    @Operation(summary = "Create Kafka topic for parent instance")
    @PostMapping(value = ENDPOINT_NAME + "/topic/{parentId}")
    @ApiResponse(responseCode = "204", description = "Topic created")
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<Void> createTopicForInstance(@PathVariable Long parentId) throws ExecutionException, InterruptedException {
        assetInstanceService.createTopicForInstance(parentId);
        return ResponseEntity.noContent().build();
    }
}