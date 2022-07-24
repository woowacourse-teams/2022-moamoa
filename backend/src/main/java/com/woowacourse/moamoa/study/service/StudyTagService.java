package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.study.controller.request.TagRequest;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.query.StudyDao;
import com.woowacourse.moamoa.study.domain.query.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.study.service.response.StudyResponse;
import com.woowacourse.moamoa.tag.domain.repository.TagRepository;
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

    private final StudyDao studyDao;
    private final StudyRepository studyRepository;
    private final TagRepository tagRepository;

    public StudiesResponse searchBy(final String title, final TagRequest tagRequest, final Pageable pageable) {
        if (tagRequest == null || tagRequest.isEmpty()) {
            return searchWithoutTag(title, pageable);
        }

        final StudySearchCondition condition = new StudySearchCondition(tagRequest.getGeneration(),
                tagRequest.getArea(), tagRequest.getSubject(), title);

        return studyDao.searchBy(condition, pageable);
    }

    private StudiesResponse searchWithoutTag(final String title, final Pageable pageable) {
        final Slice<Study> slice = studyRepository.findByDetailsTitleContainingIgnoreCase(title.trim(), pageable);
        final List<StudyResponse> studies = slice
                .map(StudyResponse::new)
                .getContent();
        return new StudiesResponse(studies, slice.hasNext());
    }
}
