package com.rtic.webhub.tag.service;

import com.rtic.webhub.tag.entity.ContentTag;
import com.rtic.webhub.tag.repository.ContentTagRepository;
import com.rtic.webhub.tag.util.TextNormalizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTagService {

    @Autowired
    private ContentTagRepository contentTagRepository;

    // Tạo mới ContentTag (có xử lý normalize)
    public ContentTag create(ContentTag tag) {
        if (contentTagRepository.existsByName(tag.getName())) {
            throw new IllegalArgumentException("A tag with this name already exists.");
        }

        tag.setNameNormalized(TextNormalizer.normalize(tag.getName()));
        tag.setSlugNormalized(TextNormalizer.normalize(tag.getSlug()));
        tag.setDescriptionNormalized(TextNormalizer.normalize(tag.getDescription()));

        return contentTagRepository.save(tag);
    }

    // Lấy toàn bộ tags
    public List<ContentTag> getAll() {
        return contentTagRepository.findAll();
    }

    // Lấy tag theo ID
    public ContentTag getById(Long id) {
        return contentTagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content tag not found with ID: " + id));
    }

    // Cập nhật ContentTag (có xử lý normalize)
    public ContentTag update(Long id, ContentTag updatedTag) {
        ContentTag tag = getById(id);

        tag.setName(updatedTag.getName());
        tag.setSlug(updatedTag.getSlug());
        tag.setDescription(updatedTag.getDescription());

        tag.setNameNormalized(TextNormalizer.normalize(updatedTag.getName()));
        tag.setSlugNormalized(TextNormalizer.normalize(updatedTag.getSlug()));
        tag.setDescriptionNormalized(TextNormalizer.normalize(updatedTag.getDescription()));

        return contentTagRepository.save(tag);
    }

    // Xóa theo ID
    public void delete(Long id) {
        if (!contentTagRepository.existsById(id)) {
            throw new RuntimeException("Cannot delete. Content tag not found with ID: " + id);
        }
        contentTagRepository.deleteById(id);
    }

    // Đã fix lại tìm iếm keywword không dấu
    // Tìm kiếm tags theo từ khóa (có normalize)
    public Page<ContentTag> search(String keyword, Pageable pageable) {
        String normalizedKeyword = TextNormalizer.normalize(keyword);
        return contentTagRepository.searchTags(normalizedKeyword, pageable);
    }
}