package com.woowacourse.moamoa.study.domain.studytag.repository;

import static com.woowacourse.moamoa.study.domain.study.QStudy.study;
import static com.woowacourse.moamoa.study.domain.studytag.QStudyTag.studyTag;
import static com.woowacourse.moamoa.tag.domain.QTag.tag;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woowacourse.moamoa.study.domain.study.Study;
import com.woowacourse.moamoa.study.domain.studytag.StudySearchCondition;
import com.woowacourse.moamoa.study.domain.studytag.StudySlice;
import com.woowacourse.moamoa.tag.domain.Category;
import com.woowacourse.moamoa.tag.domain.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

public class CustomStudyTagRepositoryImpl implements CustomStudyTagRepository {

    private final JPAQueryFactory queryFactory;

    public CustomStudyTagRepositoryImpl(final EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public StudySlice searchBy(StudySearchCondition condition, Pageable pageable) {
        final List<Study> studies = queryFactory
                .select(study)
                .from(studyTag)
                .where(studyTitleEq(condition.getTitle()),
                        studyTag.study.in(findFilteredStudy(condition.getTags())))
                .join(studyTag.study, study)
                .join(studyTag.tag, tag)
                .distinct()
                .offset(pageable.getOffset()).limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = studies.size() > pageable.getPageSize();
        removeLastOne(studies, hasNext);

        return new StudySlice(studies, hasNext);
    }

    private Predicate studyTitleEq(final String title) {
        return hasText(title) ? studyTag.study.title.containsIgnoreCase(title) : null;
    }

    private List<Study> findFilteredStudy(final List<Tag> tags) {
        final List<Category> categories = findCategories(tags);
        final List<Study> result = new ArrayList<>();

        for (Category category : categories) {
            final List<Tag> categorizedTags = makeCategorizedFilters(tags, category);
            final List<Study> studies = findStudyWithFilter(categorizedTags);
            if (result.isEmpty()) {
                result.addAll(studies);
            }
            result.removeIf(finalStudy -> !studies.contains(finalStudy));
        }

        return result;
    }

    private List<Category> findCategories(final List<Tag> tags) {
        return tags.stream()
                .map(Tag::getCategory)
                .collect(toList());
    }

    private List<Study> findStudyWithFilter(final List<Tag> tags) {
        return queryFactory.select(study)
                .from(studyTag)
                .where(categorizedFilterEq(tags))
                .join(studyTag.study, study)
                .fetch();
    }

    private List<Tag> makeCategorizedFilters(final List<Tag> tags, final Category category) {
        List<Tag> categorizedTags = new ArrayList<>();
        for (Tag tag : tags) {
            addFilter(category, categorizedTags, tag);
        }
        return categorizedTags;
    }

    private void addFilter(final Category category, final List<Tag> categorizedTags, final Tag tag) {
        if (category.getId().equals(tag.getCategory().getId())) {
            categorizedTags.add(tag);
        }
    }

    private BooleanBuilder categorizedFilterEq(final List<Tag> tags) {
        final BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (Tag tag : tags) {
            booleanBuilder.or(studyTag.tag.eq(tag));
        }

        return booleanBuilder;
    }

    private void removeLastOne(final List<Study> studies, final boolean hasNext) {
        if (hasNext) {
            studies.remove(studies.size() - 1);
        }
    }
}
