package com.woowacourse.moamoa.filter.service;

import com.woowacourse.moamoa.filter.infra.FilterResponseDao;
import com.woowacourse.moamoa.filter.infra.response.FilterResponse;
import com.woowacourse.moamoa.filter.service.response.FiltersResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FilterService {

    private final FilterResponseDao filterResponseDao;

    public FiltersResponse getFilters(final String name) {
        final List<FilterResponse> tagsResponse = filterResponseDao.findAll(name.trim());
        return new FiltersResponse(tagsResponse);
    }
}
