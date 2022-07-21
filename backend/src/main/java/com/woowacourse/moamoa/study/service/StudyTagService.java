package com.woowacourse.moamoa.study.service;

import static java.util.stream.Collectors.toList;

import com.woowacourse.moamoa.tag.domain.Tag;
import com.woowacourse.moamoa.tag.domain.repository.TagRepository;
import com.woowacourse.moamoa.tag.exception.TagNotExistException;
import com.woowacourse.moamoa.study.controller.request.TagRequest;
import com.woowacourse.moamoa.study.domain.study.Study;
import com.woowacourse.moamoa.study.domain.study.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.study.service.response.StudyResponse;
import com.woowacourse.moamoa.study.domain.studytag.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.studytag.StudySlice;
import com.woowacourse.moamoa.study.domain.studytag.repository.StudyTagRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudyTagService {

    private final StudyTagRepository studyTagRepository;
    private final StudyRepository studyRepository;
    private final TagRepository tagRepository;

    public StudiesResponse searchBy(final String title, final TagRequest tagRequest, final Pageable pageable) {
        if (tagRequest == null || tagRequest.isEmpty()) {
            return searchWithoutFilter(title, pageable);
        }
        final List<Tag> tags = getTagFromId(tagRequest);

        final StudySearchCondition condition = new StudySearchCondition(title, tags);
        final StudySlice studySlice = studyTagRepository.searchBy(condition, pageable);

        final List<StudyResponse> studies = extractStudyResponses(studySlice);
        final boolean hasNext = studySlice.isHasNext();

        return new StudiesResponse(studies, hasNext);
    }

    private StudiesResponse searchWithoutFilter(final String title, final Pageable pageable) {
        final Slice<Study> slice = studyRepository.findByDetailsTitleContainingIgnoreCase(title.trim(), pageable);
        final List<StudyResponse> studies = slice
                .map(StudyResponse::new)
                .getContent();
        return new StudiesResponse(studies, slice.hasNext());
    }

    private List<Tag> getTagFromId(final TagRequest tagRequest) {
        final List<Long> tagIds = tagRequest.getTagIds();
        final List<Tag> tags = new ArrayList<>();

        for (Long tagId : tagIds) {
            final Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(TagNotExistException::new);
            tags.add(tag);
        }

        return tags;
    }

    private List<StudyResponse> extractStudyResponses(final StudySlice studySlice) {
        return studySlice.getStudies()
                .stream()
                .map(StudyResponse::new)
                .collect(toList());
    }
}
