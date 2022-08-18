package com.woowacourse.moamoa.tag.service;

import com.woowacourse.moamoa.tag.query.TagDao;
import com.woowacourse.moamoa.tag.query.request.CategoryIdRequest;
import com.woowacourse.moamoa.tag.query.response.TagData;
import com.woowacourse.moamoa.tag.service.response.TagsResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SearchingTagService {

    private final TagDao tagDao;

    public SearchingTagService(final TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public TagsResponse getBy(String shortName, CategoryIdRequest categoryId) {
        final List<TagData> tagsResponse = tagDao.searchByShortNameAndCategoryId(shortName.trim(), categoryId);
        return new TagsResponse(tagsResponse);
    }
}
