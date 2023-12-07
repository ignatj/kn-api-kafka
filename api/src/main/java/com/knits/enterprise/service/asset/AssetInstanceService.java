package com.knits.enterprise.service.asset;

import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.asset.AssetInstanceDto;
import com.knits.enterprise.exceptions.UserException;
import com.knits.enterprise.mapper.asset.AssetInstanceMapper;
import com.knits.enterprise.mapper.asset.AssetTechSpecMapper;
import com.knits.enterprise.model.asset.*;
import com.knits.enterprise.model.location.Building;
import com.knits.enterprise.model.location.Location;
import com.knits.enterprise.model.location.WorkingArea;
import com.knits.enterprise.repository.asset.*;
import com.knits.enterprise.repository.location.BuildingRepository;
import com.knits.enterprise.repository.location.LocationRepository;
import com.knits.enterprise.repository.location.WorkingAreaRepository;
import com.knits.enterprise.service.common.GenericService;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@AllArgsConstructor
public class AssetInstanceService extends GenericService {

    private final AssetInstanceRepository assetInstanceRepository;
    private final AssetInstanceMapper assetInstanceMapper;
    private final AssetModelRepository assetModelRepository;
    private final AssetTechSpecMapper assetTechSpecMapper;
    private final VendorRepository vendorRepository;
    private final AssetTechSpecRepository assetTechSpecRepository;
    private final OrderRepository orderRepository;
    private final LocationRepository locationRepository;
    private final BuildingRepository buildingRepository;
    private final WorkingAreaRepository workingAreaRepository;
    private final IOTMetricDataRepository iotMetricDataRepository;
    private final AdminClient adminClient;

    public AssetInstanceDto create(AssetInstanceDto assetInstanceDto) {
        String operationLog ="Request to create assetInstance : %s".formatted(assetInstanceDto.toString());
        logCurrentUser(operationLog);

        AssetInstance assetInstance = assetInstanceMapper.toEntity(assetInstanceDto);

        assetInstance.setCreatedBy(getCurrentUserAsEntity());

        AssetModel assetModel = assetModelRepository.findById(assetInstanceDto.getAssetModelId())
                .orElseThrow(() -> new UserException("Provided asset model id is wrong"));
        assetInstance.setAssetModel(assetModel);

        if (assetInstanceDto.getParentAssetInstanceId() != null) {
            AssetInstance parentInstance = assetInstanceRepository.findById(assetInstanceDto.getParentAssetInstanceId())
                    .orElseThrow(() -> new UserException("Wrong parentInstance id passed!"));
            assetInstance.setParent(parentInstance);
        }

        if (!assetInstanceDto.getTechSpecs().isEmpty()) {
            List<AssetTechSpec> techSpecs = assetTechSpecMapper.toEntities(assetInstanceDto.getTechSpecs());
            assetTechSpecRepository.saveAll(techSpecs);
            assetInstance.setTechSpecs(techSpecs);
        }

        Vendor vendor = vendorRepository.findById(assetInstanceDto.getVendorId())
                .orElseThrow(() -> new UserException("Vendor id is wrong"));
        assetInstance.setVendor(vendor);

        Location location = locationRepository.findById(assetInstanceDto.getLocationId())
                .orElseThrow(() -> new UserException("Wrong location id passed"));
        assetInstance.setLocation(location);

        if (assetInstanceDto.getBuildingId() != null) {
            Building building = buildingRepository.findById(assetInstanceDto.getBuildingId())
                    .orElseThrow(() -> new UserException("Wrong building id passed"));
            assetInstance.setBuilding(building);
        }

        if (assetInstanceDto.getWorkingAreaId() != null) {
            WorkingArea workingArea = workingAreaRepository.findById(assetInstanceDto.getWorkingAreaId())
                    .orElseThrow(() -> new UserException("Wrong working area id passed"));
            assetInstance.setWorkingArea(workingArea);
        }

        Order order = orderRepository.findById(assetInstanceDto.getOrderId())
                .orElseThrow(() -> new UserException("Wrong order id passed!"));
        assetInstance.setOrder(order);

        return assetInstanceMapper.toDto(assetInstanceRepository.save(assetInstance));
    }

    public void partialUpdate(Long id, AssetInstanceDto assetInstanceDto) {
        String operationLog ="Request to partially update assetInstance with id: %s".formatted(id);
        logCurrentUser(operationLog);
        AssetInstance assetInstance = assetInstanceRepository.findById(id)
                .orElseThrow(() -> new UserException("Asset instance not found", 404));
        assetInstanceMapper.partialUpdate(assetInstance, assetInstanceDto);
    }

    public void deactivate(Long id) {
        String operationLog ="Request to deactivate assetInstance with id: %s".formatted(id);
        logCurrentUser(operationLog);
        if (!assetInstanceRepository.existsById(id)) {
            throw new UserException("Asset instance not found", 404);
        }
        assetInstanceRepository.deleteById(id);
    }

    public List<AssetInstanceDto> getAllActive() {
        return assetInstanceMapper.toDtos(assetInstanceRepository.findAllActive());
    }

    @KafkaListener(topicPattern = "assetId-\\d+", properties = "metadata.max.age.ms:6000")
    public void receiveAssetMetrics(@Payload String metricData,
                                    @Header("sensor_timestamp") String timestamp,
                                    @Header("machine_id") String machineId,
                                    @Header("device_id") String deviceId,
                                    @Header("status") String status,
                                    @Header("metric_name") String metricName) {
        IOTMetricData iotMetricData = IOTMetricData.builder()
                .timestamp(LocalDateTime.parse(timestamp, Constants.TIME_FORMATTER))
                .machine(assetInstanceRepository.getById(Long.valueOf(machineId)))
                .device(assetInstanceRepository.getById(Long.valueOf(deviceId)))
                .status(status)
                .metricName(metricName)
                .metricJsonPayload(metricData)
                .build();
        iotMetricDataRepository.save(iotMetricData);
    }

    public void createTopicForInstance(Long parentId) throws ExecutionException, InterruptedException {
        int defaultPartitions = 3;
        short defaultReplicas = 3;
        String defaultISRReplicas = "2";

        AssetInstance assetInstance = assetInstanceRepository.findById(parentId)
                .orElseThrow(() -> new UserException("Wrong asset parent id provided"));

        if (assetInstance.getParent() != null) {
            throw new UserException("Only parent asset instance can have topic assigned");
        }

        String topicName = "assetId-" + String.valueOf(parentId);

        if (adminClient.listTopics().names().get().contains(topicName)) {
            throw new UserException("Topic for provided instance already exists");
        }

        NewTopic newTopic = new NewTopic(topicName, defaultPartitions, defaultReplicas);
        newTopic.configs(Collections.singletonMap(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, defaultISRReplicas));
        adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
    }
}