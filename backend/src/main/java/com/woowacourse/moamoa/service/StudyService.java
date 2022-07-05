package com.woowacourse.moamoa.service;

import com.woowacourse.moamoa.controller.dto.StudiesResponse;
import com.woowacourse.moamoa.controller.dto.StudyResponse;
import com.woowacourse.moamoa.domain.Study;
import com.woowacourse.moamoa.repository.StudyRepository;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public StudiesResponse getStudies(Pageable pageable) {
        final Slice<Study> slice = studyRepository.findAllBy(pageable);
        final List<StudyResponse> studies = slice.map(StudyResponse::new).getContent();

        return new StudiesResponse(studies, slice.hasNext());
    }
}
