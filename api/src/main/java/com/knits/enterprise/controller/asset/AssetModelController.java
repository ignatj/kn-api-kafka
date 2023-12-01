package com.knits.enterprise.controller.asset;

import com.knits.enterprise.dto.data.asset.AssetModelDto;
import com.knits.enterprise.dto.data.common.ValidationErrorDto;
import com.knits.enterprise.service.asset.AssetModelService;
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
@Tag(name = "AssetModel", description = "AssetModel endpoints")
@Slf4j
@Validated
public class AssetModelController {

    private AssetModelService assetModelService;

    private static final String ENDPOINT_NAME="/asset-models";

    @Operation(summary = "Create new AssetModel")
    @ApiResponse(responseCode = "201", description = "AssetModel is created")
    @ApiResponse(responseCode = "400", description = "Invalid data provided",
            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class)))
    @PostMapping(value = ENDPOINT_NAME, produces = {"application/json"}, consumes = {"application/json"})
    @Validated(AssetModelDto.onAssetModelCreate.class)
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<AssetModelDto> create(@Valid @RequestBody AssetModelDto assetModelDto) {
        log.debug("Http request to create {} with data: {} ",ENDPOINT_NAME,assetModelDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assetModelService.create(assetModelDto));
    }

    @Operation(summary = "Partially update existing AssetModel")
    @ApiResponse(responseCode = "204", description = "AssetModel is updated")
    @ApiResponse(responseCode = "400", description = "Invalid data provided",
            content = @Content(schema = @Schema(implementation = ValidationErrorDto.class)))
    @PatchMapping(value = ENDPOINT_NAME + "/{id}", produces = {"application/json"}, consumes = {"application/json"})
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<Void> partialUpdate(@RequestBody AssetModelDto assetModelDto, @PathVariable Long id) {
        log.debug("Http request to partialUpdate {} with request data: {} ",ENDPOINT_NAME,assetModelDto);
        assetModelService.partialUpdate(id, assetModelDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deactivate existing AssetModel")
    @ApiResponse(responseCode = "204", description = "AssetModel is deactivated")
    @ApiResponse(responseCode = "404", description = "AssetModel not found")
    @DeleteMapping(value =ENDPOINT_NAME+"/{id}")
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        log.debug("Http request to delete {} with Id: {}",ENDPOINT_NAME, id);
        assetModelService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all active AssetModels")
    @ApiResponse(responseCode = "200", description = "AssetModels Found")
    @GetMapping(value = ENDPOINT_NAME, produces = {"application/json"})
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity<List<AssetModelDto>> getAllActive() {
        log.debug("Http request to get all {}",ENDPOINT_NAME);
        return ResponseEntity.ok().body(assetModelService.getAllActive());
    }
}
