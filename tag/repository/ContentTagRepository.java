package com.rtic.webhub.tag.repository;

import com.rtic.webhub.tag.entity.ContentTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rtic.webhub.project.entity.Project;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentTagRepository extends JpaRepository<ContentTag, Long>, JpaSpecificationExecutor<ContentTag> {
    // Find by name or slug
    Optional<ContentTag> findByName(String name);
    Optional<ContentTag> findBySlug(String slug);

    // Check if exists
    boolean existsByName(String name);
    boolean existsBySlug(String slug);

// // Search tags by name or description
//     @Query("SELECT t FROM ContentTag t WHERE " +
//        "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//        "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
//        "LOWER(t.slug) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//         Page<ContentTag> searchTags(@Param("keyword") String keyword, Pageable pageable);
//Thay thế ở trên bằng đoạn này để tìm kiếm không dấu...
        @Query("SELECT t FROM ContentTag t WHERE " +
       "LOWER(t.nameNormalized) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
       "LOWER(t.slugNormalized) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
       "LOWER(t.descriptionNormalized) LIKE LOWER(CONCAT('%', :keyword, '%'))")
Page<ContentTag> searchTags(@Param("keyword") String keyword, Pageable pageable);



    // Find tags for specific document
    @Query(value = "SELECT ct.* FROM content_tags ct " +
            "JOIN taggables t ON ct.id = t.tag_id " +
            "WHERE t.taggable_id = :documentId AND t.taggable_type = 'Document'",
            nativeQuery = true)
    List<ContentTag> findTagsByDocumentId(@Param("documentId") Long documentId);

    // Find tags by multiple IDs
    List<ContentTag> findByIdIn(List<Long> ids);

    List<ContentTag> findContentTagByName(String tagName);
    /*
     * Projects's ContentTag 
     * 
     */
        // Find tags for specific project
        @Query(value = "SELECT ct.* FROM content_tags ct " +
                "JOIN taggables t ON ct.id = t.tag_id " +
                "WHERE t.taggable_id = :projectId AND t.taggable_type = 'Project'",
                nativeQuery = true)
        List<ContentTag> findTagsByProjectId(@Param("projectId") Long projectId);

        // Find projects by multiple tags
        @Query(
            value = "SELECT p.* FROM projects p " +
                    "JOIN taggables t ON p.id = t.taggable_id " +
                    "WHERE t.tag_id IN (:tagIds) AND t.taggable_type = 'Project'",
            countQuery = "SELECT COUNT(DISTINCT p.id) FROM projects p " +
                    "JOIN taggables t ON p.id = t.taggable_id " +
                    "WHERE t.tag_id IN (:tagIds) AND t.taggable_type = 'Project'",
            nativeQuery = true
        )
        Page<Project> findProjectsByTagIds(@Param("tagIds") List<Long> tagIds, Pageable pageable);
        List<ContentTag> findByNameIn(List<String> contentTagNames);
        @Modifying
        @Query(value = "INSERT INTO taggables (tag_id, taggable_id, taggable_type) VALUES (:tagId, :projectId, :taggableType)",
                nativeQuery = true)
        void insertTaggable(@Param("tagId") Long tagId, @Param("projectId") Long projectId, @Param("taggableType") String taggableType);
}