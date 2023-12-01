package com.knits.enterprise.iot_data_simulator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knits.enterprise.config.Constants;
import com.knits.enterprise.dto.data.asset.iot_data.AxisPayloadDto;
import com.knits.enterprise.dto.data.asset.iot_data.ValueUnitPayloadDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class SensorDataProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String CAR_ASSET_ID = "1";

    private static final Double VARIATION_PERCENTAGE = 0.1;

    private static final Double TEMPERATURE_STANDARD = 50.0;
    private static final Double VIBRATION_STANDARD = 1.0;
    private static final Double PRESSURE_STANDARD = 100.0;
    private static final Double HUMIDITY_STANDARD = 50.0;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(fixedRate = 3000)
    void temperatureDataProducer() throws JsonProcessingException {
        String temperatureSensorAssetId = "2";

        ValueUnitPayloadDto metricData = ValueUnitPayloadDto.builder()
                .value(generateValue(TEMPERATURE_STANDARD).toString())
                .unit("Celsius")
                .build();

        String jsonPayload = objectMapper.writeValueAsString(metricData);

        kafkaTemplate.send(generateMessage(jsonPayload, temperatureSensorAssetId, "temperature"));
    }

    @Scheduled(fixedRate = 2000)
    void vibrationDataProducer() throws JsonProcessingException {
        String vibrationSensorAssetId = "3";

        AxisPayloadDto metricData = AxisPayloadDto.builder()
                .x_axis(generateValue(VIBRATION_STANDARD).toString())
                .y_axis(generateValue(VIBRATION_STANDARD).toString())
                .z_axis(generateValue(VIBRATION_STANDARD).toString())
                .unit("g")
                .build();

        String jsonPayload = objectMapper.writeValueAsString(metricData);

        kafkaTemplate.send(generateMessage(jsonPayload, vibrationSensorAssetId, "vibration"));
    }

    @Scheduled(fixedRate = 2500)
    void pressureDataProducer() throws JsonProcessingException {
        String pressureSensorAssetId = "4";

        ValueUnitPayloadDto metricData = ValueUnitPayloadDto.builder()
                .value(generateValue(PRESSURE_STANDARD).toString())
                .unit("kPa")
                .build();

        String jsonPayload = objectMapper.writeValueAsString(metricData);

        kafkaTemplate.send(generateMessage(jsonPayload, pressureSensorAssetId, "pressure"));
    }

    @Scheduled(fixedRate = 5000)
    void humidityDataProducer() throws JsonProcessingException {
        String humiditySensorAssetId = "5";

        ValueUnitPayloadDto metricData = ValueUnitPayloadDto.builder()
                .value(generateValue(HUMIDITY_STANDARD).toString())
                .unit("percent")
                .build();

        String jsonPayload = objectMapper.writeValueAsString(metricData);

        kafkaTemplate.send(generateMessage(jsonPayload, humiditySensorAssetId, "humidity"));
    }

    private static Double generateValue(Double standard) {
        double randomVariationScalar = (Math.random() - 0.5) * 2 * VARIATION_PERCENTAGE;
        return standard * (1 + randomVariationScalar);
    }

    private static Message<String> generateMessage(String jsonPayload,
                                                   String deviceId,
                                                   String metricName) {
        String timestamp = LocalDateTime.now().format(Constants.TIME_FORMATTER);
        return MessageBuilder
                .withPayload(jsonPayload)
                .setHeader(KafkaHeaders.TOPIC, "AmmoliteSensorData")
                .setHeader("sensor_timestamp", timestamp)
                .setHeader("machine_id", CAR_ASSET_ID)
                .setHeader("device_id", deviceId)
                .setHeader("status", "normal")
                .setHeader("metric_name", metricName)
                .build();
    }
}
