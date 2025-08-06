package com.rtic.webhub.tag.service;

import com.rtic.webhub.tag.repository.ContentTagRepository;
import com.rtic.webhub.tag.entity.Taggable;
import com.rtic.webhub.tag.entity.TaggableEmbeddedId;
import com.rtic.webhub.tag.entity.ContentTag;
import com.rtic.webhub.tag.repository.TaggableRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TaggableService {
    @Autowired
    private TaggableRepository taggableRepository;
    @Autowired
    private ContentTagRepository contentTagRepository;

    // Get list of content tags by taggable type and taggable ID
    public List<ContentTag> getContentTagsByTypeAndId(String taggableType, Long taggableId) {
        List<Taggable> taggables = taggableRepository.findByTaggableTypeAndTaggableId(taggableType, taggableId);
        List<Long> tagIds = taggables.stream()
                .map(taggable -> taggable.getId().getTagId())
                .toList();
        return contentTagRepository.findByIdIn(tagIds);
    }

    // Get list of taggable IDs by taggable type and list content tag names
    public List<Long> getTaggableIdsByTaggableTypeAndContentTagNames(String taggableType, List<String> contentTagNames) {
        List<ContentTag> contentTags = contentTagRepository.findByNameIn(contentTagNames);
        if (contentTags.isEmpty()) {
            return Collections.emptyList();
        }
        for (String contentTagName : contentTagNames) {
            if (contentTags.stream().noneMatch(tag -> tag.getName().equals(contentTagName))) {
                return Collections.emptyList();
            }
        }
        // find taggables id by type and tag IDs
        if (contentTags.isEmpty()) {
            return Collections.emptyList();
        }
        // Get tag IDs from content tags
        Set<ContentTag> contentTagSet = new HashSet<>(contentTags);
        if (contentTagSet.isEmpty()) {
            return Collections.emptyList();
        }
        // Find taggables by type and tag IDs
        Set<Long> taggableIdSet = new HashSet<>();
        for (ContentTag contentTag : contentTagSet) {
            List<Taggable> taggables = taggableRepository.findByTagIdAndTaggableType(contentTag.getId(), taggableType);
            for (Taggable taggable : taggables) {
                taggableIdSet.add(taggable.getId().getTaggableId());
            }
        }
        return new ArrayList<>(taggableIdSet);
    }
    // Add a new list taggable for a content tag name
    @Transactional
    public void addTaggable(Long taggableId, String taggableType, List<String> contentTagNames) {
        if (contentTagNames == null || taggableId == null || taggableType == null) {
            throw new IllegalArgumentException("ContentTag, taggableId, and taggableType must not be null");
        }
        for (String contentTagName : contentTagNames) {
            ContentTag contentTag = contentTagRepository.findByName(contentTagName)
                    .orElseThrow(() -> new IllegalArgumentException("ContentTag not found: " + contentTagName));
            Taggable taggable = new Taggable();
            TaggableEmbeddedId id = new TaggableEmbeddedId();
            id.setTaggableId(taggableId);
            id.setTaggableType(taggableType);
            id.setTagId(contentTag.getId());
            // Kiểm tra xem khóa chính đã tồn tại chưa
            if (taggableRepository.existsById(id)) {
                throw new IllegalArgumentException("Taggable with ID " + id + " already exists.");
            }
            taggable.setId(
                    id
            );
            System.out.println("Saving taggable: " + taggable);
            System.out.println("Id taggable: " + taggable.getId());
            // taggable.setId(id);
            taggableRepository.save(taggable);
            // Log the saved taggable and information saved
            System.out.println("Taggable saved successfully: " + taggableRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Failed to save taggable with ID: " + id)));
        }

    }
    // Remove a taggable by taggable ID and type
    @Transactional
    public void removeTaggable(Long taggableId, String taggableType, List<String> contentTagNames) {
        if (contentTagNames == null || taggableId == null || taggableType == null) {
            throw new IllegalArgumentException("ContentTag, taggableId, and taggableType must not be null");
        }
        for (String contentTagName : contentTagNames) {
            ContentTag contentTag = contentTagRepository.findByName(contentTagName)
                    .orElseThrow(() -> new IllegalArgumentException("ContentTag not found: " + contentTagName));
            // Remove the taggable by taggable ID, type, and content tag ID
            // dùng remove để xóa taggable
            TaggableEmbeddedId id = new TaggableEmbeddedId();
            id.setTaggableId(taggableId);
            id.setTaggableType(taggableType);
            id.setTagId(contentTag.getId());
            taggableRepository.deleteById(id);
        }
    }

    // Update a taggable for a content tag name
    public void updateTaggable(Long taggableId, String taggableType, List<String> contentTagNames) {
        if (contentTagNames == null || taggableId == null || taggableType == null) {
            throw new IllegalArgumentException("ContentTag, taggableId, and taggableType must not be null");
        }
        // Remove existing taggables
        taggableRepository.deleteByTaggableIdAndTaggableType(taggableId, taggableType);
        // Add new taggables
        addTaggable(taggableId, taggableType, contentTagNames);
    }

    public List<String> getContentTagNameByTaggableIdAndType(Long projectId, String taggableType) {
        List<Taggable> taggables = taggableRepository.findByTaggableTypeAndTaggableId(taggableType, projectId);
        if (taggables.isEmpty()) {
            return Collections.emptyList();
        }
        Set<Long> tagIds = new HashSet<>();
        for (Taggable taggable : taggables) {
            tagIds.add(taggable.getId().getTagId());
        }
        List<ContentTag> contentTags = contentTagRepository.findByIdIn(new ArrayList<>(tagIds));
        return contentTags.stream().map(ContentTag::getName).toList();
    }

    public List<Long> getTaggableIdsByContentTagNameAndTaggableType(List<String> contentTagNames, String string) {
        List<ContentTag> contentTags = contentTagRepository.findByNameIn(contentTagNames);
        if (contentTags.isEmpty()) {
            return Collections.emptyList();
        }
        Set<Long> taggableIds = new HashSet<>();
        for (ContentTag contentTag : contentTags) {
            List<Taggable> taggables = taggableRepository.findByTagIdAndTaggableType(contentTag.getId(), string);
            for (Taggable taggable : taggables) {
                taggableIds.add(taggable.getId().getTaggableId());
            }
        }
        return new ArrayList<>(taggableIds);
    }
}

