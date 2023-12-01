package com.knits.enterprise.dto.data.asset.iot_data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValueUnitPayloadDto {

    private String value;

    private String unit;
}
