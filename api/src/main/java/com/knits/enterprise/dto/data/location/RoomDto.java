package com.knits.enterprise.dto.data.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder=true)
@AllArgsConstructor
public class RoomDto extends WorkingAreaDto {
}
