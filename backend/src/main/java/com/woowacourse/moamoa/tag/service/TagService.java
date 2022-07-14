package com.woowacourse.moamoa.tag.service;

import com.woowacourse.moamoa.tag.domain.Tag;
import com.woowacourse.moamoa.tag.domain.repository.TagRepository;
import com.woowacourse.moamoa.tag.service.response.TagResponse;
import com.woowacourse.moamoa.tag.service.response.TagsResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    public TagsResponse getTags(final String tagName) {
        final List<Tag> tags = tagRepository.findAllByNameContainingIgnoreCase(tagName.trim());
        final List<TagResponse> tagsResponse = tags.stream()
                .map(TagResponse::new)
                .collect(Collectors.toList());
        return new TagsResponse(tagsResponse);
    }
}
