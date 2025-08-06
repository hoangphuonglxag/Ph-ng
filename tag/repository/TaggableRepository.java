package com.rtic.webhub.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rtic.webhub.tag.entity.Taggable;
import com.rtic.webhub.tag.entity.TaggableEmbeddedId;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaggableRepository extends JpaRepository<Taggable, TaggableEmbeddedId>, JpaSpecificationExecutor<Taggable> {

    // Tìm taggable theo TaggableId
    @Query("SELECT t FROM Taggable t WHERE t.id.taggableId = :taggableId")
    Optional<Taggable> findByTaggableId(@Param("taggableId") Long taggableId);

    // Tìm tất cả taggables
    @Query("SELECT t FROM Taggable t")
    List<Taggable> findAllTaggables();

    // Tìm tất cả taggables theo tag ID
    @Query("SELECT t FROM Taggable t WHERE t.id.tagId = :tagId")
    List<Taggable> findByTagId(@Param("tagId") Long tagId);

    // Tìm tất cả taggables theo TaggableType
    @Query("SELECT t FROM Taggable t WHERE t.id.taggableType = :taggableType")
    List<Taggable> findByTaggableType(@Param("taggableType") String taggableType);

    // Tìm tất cả taggables theo tag ID và TaggableType
    @Query("SELECT t FROM Taggable t WHERE t.id.tagId = :tagId AND t.id.taggableType = :taggableType")
    List<Taggable> findByTagIdAndTaggableType(@Param("tagId") Long tagId, @Param("taggableType") String taggableType);

    // Tìm tất cả tag IDs theo TaggableId và TaggableType
    @Query("SELECT t.id.tagId FROM Taggable t WHERE t.id.taggableId = :taggableId AND t.id.taggableType = :taggableType")
    List<Long> findTagIdsByTaggableAndTaggableType(@Param("taggableId") Long taggableId, @Param("taggableType") String taggableType);

    // Tìm tất cả taggables theo TaggableType và TaggableId
    @Query("SELECT t FROM Taggable t WHERE t.id.taggableType = :taggableType AND t.id.taggableId = :taggableId")
    List<Taggable> findByTaggableTypeAndTaggableId(@Param("taggableType") String taggableType, @Param("taggableId") Long taggableId);

    // Xóa taggable theo TaggableId, TaggableType, và TagId
    @Modifying
    @Query("DELETE FROM Taggable t WHERE t.id.taggableId = :taggableId AND t.id.taggableType = :taggableType AND t.id.tagId = :tagId")
    void deleteByTaggableIdAndTaggableTypeAndTagId(@Param("taggableId") Long taggableId, @Param("taggableType") String taggableType, @Param("tagId") Long tagId);

    // Xóa tất cả taggables theo TaggableId và TaggableType
    @Modifying
    @Query("DELETE FROM Taggable t WHERE t.id.taggableId = :taggableId AND t.id.taggableType = :taggableType")
    void deleteByTaggableIdAndTaggableType(@Param("taggableId") Long taggableId, @Param("taggableType") String taggableType);
}
