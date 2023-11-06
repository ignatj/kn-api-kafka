package com.knits.enterprise.dto.data.location;

import com.knits.enterprise.dto.data.common.AddressDto;
import com.knits.enterprise.dto.data.common.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder=true)
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto extends AbstractAuditableDto {
    private String name;
    private AddressDto address;
    private String ownership;
    private boolean mapCoordinates;
    private String latitude;
    private String longitude;
    private String realEstate;
    private boolean isDeleted;

}
