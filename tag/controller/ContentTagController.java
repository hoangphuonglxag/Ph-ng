package com.rtic.webhub.tag.controller;

import com.rtic.webhub.tag.entity.ContentTag;
import com.rtic.webhub.tag.service.ContentTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content-tags")
@Tag(name = "Content Tag", description = "CRUD APIs for content tags")
public class ContentTagController {

    @Autowired
    private ContentTagService contentTagService;

    @Operation(summary = "Create a new content tag")
    @PostMapping
    public ContentTag create(@RequestBody ContentTag tag) {
        return contentTagService.create(tag);
    }

    @Operation(summary = "Get all content tags")
    @GetMapping
    public List<ContentTag> getAll() {
        return contentTagService.getAll();
    }

    @Operation(summary = "Get a content tag by ID")
    @GetMapping("/{id}")
    public ContentTag getById(@PathVariable Long id) {
        return contentTagService.getById(id);
    }

    @Operation(summary = "Update a content tag by ID")
    @PutMapping("/{id}")
    public ContentTag update(@PathVariable Long id, @RequestBody ContentTag tag) {
        return contentTagService.update(id, tag);
    }

    @Operation(summary = "Delete a content tag by ID")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contentTagService.delete(id);
    }

    @Operation(summary = "Search content tags by keyword")
    @GetMapping("/search")
    public Page<ContentTag> search(
            @RequestParam String keyword,
            Pageable pageable
    ) {
        return contentTagService.search(keyword, pageable);
    }
}