package com.woowacourse.moamoa.study.domain.query;

import static com.woowacourse.moamoa.tag.domain.CategoryName.AREA;
import static com.woowacourse.moamoa.tag.domain.CategoryName.GENERATION;
import static com.woowacourse.moamoa.tag.domain.CategoryName.SUBJECT;

import com.woowacourse.moamoa.tag.domain.CategoryName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class StudySearchCondition {

    private final Map<CategoryName, List<Long>> tags = new HashMap<>();
    private final String title;

    public StudySearchCondition(final List<Long> generationIds, final List<Long> areaIds, final List<Long> tagIds,
                                final String title) {
        tags.put(GENERATION, generationIds);
        tags.put(AREA, areaIds);
        tags.put(SUBJECT, tagIds);
        this.title = title;
    }

    public boolean hasBy(CategoryName name) {
        return !tags.get(name).isEmpty();
    }

    public List<Long> getTagIdsBy(CategoryName name) {
        return tags.get(name);
    }

    public static StudySearchCondition emptyFilters(final String title) {
        return new StudySearchCondition(List.of(), List.of(), List.of(), title);
    }

    public String getTitle() {
        return title;
    }
}
