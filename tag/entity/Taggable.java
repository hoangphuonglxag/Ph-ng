package com.rtic.webhub.tag.entity;

import com.rtic.webhub.blog.entity.Blog;
import com.rtic.webhub.document.entity.Document;
import com.rtic.webhub.event.entity.Event;
import com.rtic.webhub.project.entity.Project;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "taggables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Taggable {

    @EmbeddedId
    private TaggableId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", nullable = false)
    private ContentTag tag;

    /**
     * Custom resolver để lấy entity thực sự.
     * Noote: EntityManager cần được truyền vào và phương thức này không nên gọi trong quá trình serialize entity.
     */
    @Transient
    public Object getTaggableEntity(EntityManager em) {
        if (id == null || id.getTaggableType() == null) {
            throw new IllegalStateException("TaggableId or taggableType is null");
        }
        switch (id.getTaggableType()) {
            case "BlogPost":
                return em.find(Blog.class, id.getTaggableId());
            case "Project":
                return em.find(Project.class, id.getTaggableId());
            case "Event":
                return em.find(Event.class, id.getTaggableId());
            case "Document":
                return em.find(Document.class, id.getTaggableId());
            default:
                throw new IllegalArgumentException("Unknown taggable type: " + id.getTaggableType());
        }
    }

    // Thêm phần Optionla để tạo thêm tiện ích tạo mới Taggable
    public Taggable(ContentTag tag, Long taggableId, String taggableType) {
        this.id = new TaggableId(tag.getId(), taggableId, taggableType);
        this.tag = tag;
    }
}