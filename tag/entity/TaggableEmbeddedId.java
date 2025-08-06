package com.rtic.webhub.tag.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TaggableEmbeddedId implements Serializable {
    @JsonProperty("tag_id")
    @Column(name = "tag_id")
    private Long tagId;

    @JsonProperty("taggable_id")
    @Column(name = "taggable_id")
    private Long taggableId;

    @JsonProperty("taggable_type")
    @Column(name = "taggable_type")
    private String taggableType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaggableEmbeddedId)) return false;
        TaggableEmbeddedId that = (TaggableEmbeddedId) o;
        return Objects.equals(tagId, that.tagId) &&
                Objects.equals(taggableId, that.taggableId) &&
                Objects.equals(taggableType, that.taggableType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, taggableId, taggableType);
    }
}
