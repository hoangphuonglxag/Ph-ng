package com.rtic.webhub.tag.entity;

import com.rtic.webhub.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "content_tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContentTag extends BaseEntity {

    @NotBlank(message = "Name không được để trống")
    @Size(max = 100, message = "Name tối đa 100 ký tự")
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @NotBlank(message = "Slug không được để trống")
    @Size(max = 100, message = "Slug tối đa 100 ký tự")
    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Size(max = 500, message = "Description tối đa 500 ký tự")
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Taggable> taggables = new HashSet<>();
}