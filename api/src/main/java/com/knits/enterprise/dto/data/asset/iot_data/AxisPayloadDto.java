package com.knits.enterprise.dto.data.asset.iot_data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AxisPayloadDto {

    private String x_axis;

    private String y_axis;

    private String z_axis;

    private String unit;
}
