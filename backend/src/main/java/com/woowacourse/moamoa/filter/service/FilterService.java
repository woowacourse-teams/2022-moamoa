package com.woowacourse.moamoa.filter.service;

import com.woowacourse.moamoa.filter.domain.Filter;
import com.woowacourse.moamoa.filter.domain.repository.FilterRepository;
import com.woowacourse.moamoa.filter.service.response.FilterResponse;
import com.woowacourse.moamoa.filter.service.response.FiltersResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FilterService {

    private final FilterRepository filterRepository;

    public FiltersResponse getTags(final String tagName) {
        final List<Filter> filters = filterRepository.findAllByNameContainingIgnoreCase(tagName.trim());
        final List<FilterResponse> tagsResponse = filters.stream()
                .map(FilterResponse::new)
                .collect(Collectors.toList());
        return new FiltersResponse(tagsResponse);
    }
}
