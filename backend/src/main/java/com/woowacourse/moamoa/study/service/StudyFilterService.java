package com.woowacourse.moamoa.study.service;

import static java.util.stream.Collectors.toList;

import com.woowacourse.moamoa.filter.domain.Filter;
import com.woowacourse.moamoa.filter.domain.repository.FilterRepository;
import com.woowacourse.moamoa.study.controller.request.FilterRequest;
import com.woowacourse.moamoa.study.domain.study.Study;
import com.woowacourse.moamoa.study.domain.study.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.study.service.response.StudyResponse;
import com.woowacourse.moamoa.study.domain.studyfilter.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.studyfilter.StudySlice;
import com.woowacourse.moamoa.study.domain.studyfilter.repository.StudyFilterRepository;
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
public class StudyFilterService {

    private final StudyFilterRepository studyFilterRepository;
    private final StudyRepository studyRepository;
    private final FilterRepository filterRepository;

    public StudiesResponse searchBy(final String title, final FilterRequest filterRequest, final Pageable pageable) {
        if (filterRequest == null || filterRequest.isEmpty()) {
            return searchWithoutFilter(title, pageable);
        }
        final List<Filter> filters = getFilterFromName(filterRequest);

        final StudySearchCondition condition = new StudySearchCondition(title, filters);
        final StudySlice studySlice = studyFilterRepository.searchBy(condition, pageable);

        final List<StudyResponse> studies = extractStudyResponses(studySlice);
        final boolean hasNext = studySlice.isHasNext();

        return new StudiesResponse(studies, hasNext);
    }

    private StudiesResponse searchWithoutFilter(final String title, final Pageable pageable) {
        final Slice<Study> slice = studyRepository.findByTitleContainingIgnoreCase(title.trim(), pageable);
        final List<StudyResponse> studies = slice
                .map(StudyResponse::new)
                .getContent();
        return new StudiesResponse(studies, slice.hasNext());
    }

    private List<Filter> getFilterFromName(final FilterRequest filterRequest) {
        final List<String> filterNames = filterRequest.getFilterNames();
        final List<Filter> filters = new ArrayList<>();

        for (String filterName : filterNames) {
            final Filter filter = filterRepository.findByName(filterName)
                    .orElseThrow(IllegalArgumentException::new);
            filters.add(filter);
        }

        return filters;
    }

    private List<StudyResponse> extractStudyResponses(final StudySlice studySlice) {
        return studySlice.getStudies()
                .stream()
                .map(StudyResponse::new)
                .collect(toList());
    }
}
