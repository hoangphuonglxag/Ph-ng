package com.rtic.webhub.tag.mapper;

import com.rtic.webhub.tag.dto.request.TagAddRequest;
import com.rtic.webhub.tag.dto.request.TagUpdateRequest;
import com.rtic.webhub.tag.dto.response.TagResponse;
import com.rtic.webhub.tag.entity.ContentTag;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ContentTagMapper {

    TagResponse toResponse(ContentTag entity);

    List<TagResponse> toResponseList(List<ContentTag> entities);

    List<TagResponse> toResponseList(Set<ContentTag> entities);

    ContentTag toEntity(TagAddRequest request);

    void updateEntityFromDto(TagUpdateRequest request, @MappingTarget ContentTag entity);
}