package com.rtic.webhub.tag.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Size;

@Data
public class TagUpdateRequest {

    @Size(max = 100, message = "Name tối đa 100 ký tự")
    private String name;

    @Size(max = 100, message = "Slug tối đa 100 ký tự")
    private String slug;

    @Size(max = 500, message = "Description tối đa 500 ký tự")
    private String description;
}