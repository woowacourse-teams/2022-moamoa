package com.woowacourse.moamoa.service;

import com.woowacourse.moamoa.controller.dto.StudiesResponse;
import com.woowacourse.moamoa.controller.dto.StudyResponse;
import com.woowacourse.moamoa.domain.Study;
import com.woowacourse.moamoa.repository.StudyRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public StudiesResponse getStudies(int page, int size) {
        List<Study> studies = studyRepository.findAll(page, size);
        List<StudyResponse> responses = studies.stream()
                .map(StudyResponse::new)
                .collect(Collectors.toList());
        return new StudiesResponse(responses, false);
    }
}
