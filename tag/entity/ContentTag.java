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

import com.fasterxml.jackson.annotation.JsonIgnore; //khia bao thu vien này để tránh lỗi vòng lặp vô hạn khi sử dụng Jackson để serialize/deserialize

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
    // // Lưu trữ các trường đã chuẩn hóa để tìm kiếm nhanh hơn (không dấu)
    @Column(name = "name_normalized")
    private String nameNormalized;

    @Column(name = "slug_normalized")
    private String slugNormalized;

    @Column(name = "description_normalized")
    private String descriptionNormalized;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // sử dụng @JsonIgnore để tránh vòng lặp vô hạn khi serialize/deserialize
    private Set<Taggable> taggables = new HashSet<>();
}