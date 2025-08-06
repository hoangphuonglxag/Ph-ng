
package com.rtic.webhub.tag.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "taggables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Taggable {
    @EmbeddedId
    private TaggableEmbeddedId id;
    @ManyToOne
    @MapsId("tagId")  // ánh xạ khóa chính embedded field tagId
    @JoinColumn(name = "tag_id")
    private ContentTag tag;
}