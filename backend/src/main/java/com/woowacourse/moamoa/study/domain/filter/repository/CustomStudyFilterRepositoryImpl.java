package com.woowacourse.moamoa.study.domain.filter.repository;

import static com.woowacourse.moamoa.filter.domain.QFilter.filter;
import static com.woowacourse.moamoa.study.domain.QStudy.study;
import static com.woowacourse.moamoa.studyfilter.domain.QStudyFilter.studyFilter;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.moamoa.filter.domain.Category;
import com.woowacourse.moamoa.filter.domain.Filter;
import com.woowacourse.moamoa.study.domain.Study;
import com.woowacourse.moamoa.study.domain.filter.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.filter.StudySlice;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class CustomStudyFilterRepositoryImpl implements CustomStudyFilterRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public StudySlice searchBy(StudySearchCondition condition, Pageable pageable) {
        final List<Study> studies = queryFactory
                .select(study)
                .from(studyFilter)
                .where(studyTitleEq(condition.getTitle()),
                        studyFilter.study.in(findFilteredStudy(condition.getFilters())))
                .join(studyFilter.study, study)
                .join(studyFilter.filter, filter)
                .distinct()
                .offset(pageable.getOffset()).limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = studies.size() > pageable.getPageSize();
        removeLastOne(studies, hasNext);

        return new StudySlice(studies, hasNext);
    }

    private Predicate studyTitleEq(final String title) {
        return hasText(title) ? studyFilter.study.title.containsIgnoreCase(title) : null;
    }

    private List<Study> findFilteredStudy(final List<Filter> filters) {
        final List<Category> categories = findCategories(filters);
        final List<Study> result = new ArrayList<>();

        for (Category category : categories) {
            final List<Filter> categorizedFilters = makeCategorizedFilters(filters, category);
            final List<Study> studies = findStudyWithFilter(categorizedFilters);
            if (result.isEmpty()) {
                result.addAll(studies);
            }
            result.removeIf(finalStudy -> !studies.contains(finalStudy));
        }

        return result;
    }

    private List<Category> findCategories(final List<Filter> filters) {
        return filters.stream()
                .map(Filter::getCategory)
                .collect(toList());
    }

    private List<Study> findStudyWithFilter(final List<Filter> filters) {
        return queryFactory.select(study)
                .from(studyFilter)
                .where(categorizedFilterEq(filters))
                .join(studyFilter.study, study)
                .fetch();
    }

    private List<Filter> makeCategorizedFilters(final List<Filter> filters, final Category category) {
        List<Filter> categorizedFilters = new ArrayList<>();
        for (Filter filter : filters) {
            addFilter(category, categorizedFilters, filter);
        }
        return categorizedFilters;
    }

    private void addFilter(final Category category, final List<Filter> categorizedFilters, final Filter filter) {
        if (category.getId().equals(filter.getCategory().getId())) {
            categorizedFilters.add(filter);
        }
    }

    private BooleanBuilder categorizedFilterEq(final List<Filter> filters) {
        final BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (Filter filter : filters) {
            booleanBuilder.or(studyFilter.filter.eq(filter));
        }

        return booleanBuilder;
    }

    private void removeLastOne(final List<Study> studies, final boolean hasNext) {
        if (hasNext) {
            studies.remove(studies.size() - 1);
        }
    }
}
