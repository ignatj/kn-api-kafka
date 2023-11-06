package com.knits.enterprise.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "files")
public class BinaryData extends AbstractEntity {

    private static final long serialVersionUID = 2L;

    private String title;

    private Long size;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    private byte[] bytes;
}
