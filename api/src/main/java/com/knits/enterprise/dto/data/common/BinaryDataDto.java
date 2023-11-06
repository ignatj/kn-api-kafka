package com.knits.enterprise.dto.data.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BinaryDataDto extends AbstractActiveDto {

    private String title;
    private Long size;
    private String contentType;
    private byte[] bytes;
}
