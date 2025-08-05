package com.rtic.webhub.tag.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TaggableId implements Serializable {
    private Long tagId;
    private Long taggableId;
    private String taggableType;
}