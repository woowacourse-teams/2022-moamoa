package com.woowacourse.moamoa.study.query;

import com.woowacourse.moamoa.tag.domain.CategoryName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class SearchingTags {

    private final Map<CategoryName, List<Long>> tags = new HashMap<>();

    public SearchingTags(final List<Long> generationIds, final List<Long> areaIds, final List<Long> tagIds) {
        tags.put(CategoryName.GENERATION, generationIds);
        tags.put(CategoryName.AREA, areaIds);
        tags.put(CategoryName.SUBJECT, tagIds);
    }

    public boolean hasBy(CategoryName name) {
        return !tags.get(name).isEmpty();
    }

    public List<Long> getTagIdsBy(CategoryName name) {
        return tags.get(name);
    }

    public static SearchingTags emptyTags() {
        return new SearchingTags(List.of(), List.of(), List.of());
    }
}
