package com.rtic.webhub.tag.util;

import com.rtic.webhub.tag.entity.ContentTag;
import com.rtic.webhub.tag.repository.ContentTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Component
public class ContentTagDataInitializer {

    @Autowired
    private ContentTagRepository contentTagRepository;

    @PostConstruct
    public void normalizeExistingTags() {
        List<ContentTag> tags = contentTagRepository.findAll();
        for (ContentTag tag : tags) {
            tag.setNameNormalized(TextNormalizer.normalize(tag.getName()));
            tag.setSlugNormalized(TextNormalizer.normalize(tag.getSlug()));
            tag.setDescriptionNormalized(TextNormalizer.normalize(tag.getDescription()));
        }
        contentTagRepository.saveAll(tags);
        System.out.println("Normalized content tags đã được cập nhật vào DB.");
    }
}