package com.rtic.webhub.tag.service;

import com.rtic.webhub.tag.entity.ContentTag;
import com.rtic.webhub.tag.repository.ContentTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTagService {

    @Autowired
    private ContentTagRepository contentTagRepository;

    public ContentTag create(ContentTag tag) {
        if (contentTagRepository.existsByName(tag.getName())) {
            throw new IllegalArgumentException("A tag with this name already exists.");
        }
        return contentTagRepository.save(tag);
    }

    public List<ContentTag> getAll() {
        return contentTagRepository.findAll();
    }

    public ContentTag getById(Long id) {
        return contentTagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content tag not found with ID: " + id));
    }

    public ContentTag update(Long id, ContentTag updatedTag) {
        ContentTag tag = getById(id);
        tag.setName(updatedTag.getName());
        tag.setDescription(updatedTag.getDescription());
        return contentTagRepository.save(tag);
    }

    public void delete(Long id) {
        if (!contentTagRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Content tag not found with ID: " + id);
        }
        contentTagRepository.deleteById(id);
    }

    public Page<ContentTag> search(String keyword, Pageable pageable) {
        return contentTagRepository.searchTags(keyword, pageable);
    }
}