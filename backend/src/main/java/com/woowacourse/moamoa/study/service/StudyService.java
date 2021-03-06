package com.woowacourse.moamoa.study.service;

import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.study.service.response.StudyResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(final StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public StudiesResponse getStudies(final Pageable pageable) {
        final Slice<Study> slice = studyRepository.findAll(pageable);
        final List<StudyResponse> studies = slice
                .map(StudyResponse::new)
                .getContent();
        return new StudiesResponse(studies, slice.hasNext());
    }

    public StudiesResponse searchBy(final String title, final Pageable pageable) {
        final Slice<Study> slice = studyRepository.findByTitleContainingIgnoreCase(title.trim(), pageable);
        final List<StudyResponse> studies = slice
                .map(StudyResponse::new)
                .getContent();
        return new StudiesResponse(studies, slice.hasNext());
    }
}
