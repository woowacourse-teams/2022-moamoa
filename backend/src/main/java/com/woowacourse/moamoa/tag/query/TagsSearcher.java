package com.woowacourse.moamoa.tag.query;

import com.woowacourse.moamoa.tag.domain.CategoryId;
import com.woowacourse.moamoa.tag.query.response.CategoryResponse;
import com.woowacourse.moamoa.tag.query.response.TagResponse;
import com.woowacourse.moamoa.tag.query.response.TagsResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TagsSearcher {

    public static final RowMapper<TagResponse> ROW_MAPPER = (rs, rn) -> {
        final long tagId = rs.getLong("tag_id");
        final String tagShortName = rs.getString("tag_short_name");
        final String tagFullName = rs.getString("tag_full_name");
        final long categoryId = rs.getLong("category_id");
        final String categoryName = rs.getString("category_name");

        return new TagResponse(tagId, tagShortName, tagFullName, new CategoryResponse(categoryId, categoryName));
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TagsResponse searchBy(final String tagShortName, final CategoryId categoryId) {
        final List<TagResponse> tagResponses = jdbcTemplate
                .query(sql(categoryId), params(tagShortName, categoryId), ROW_MAPPER);
        return new TagsResponse(tagResponses);
    }

    private String sql(final CategoryId categoryId) {
        String sql = "SELECT t.id as tag_id, t.short_name as tag_short_name, t.full_name as tag_full_name, "
                + "c.id as category_id, c.name as category_name "
                + "FROM tag as t JOIN category as c ON t.category_id = c.id "
                + "WHERE UPPER(t.short_name) LIKE UPPER(:name)";

        if (categoryId.isEmpty()) {
            return sql;
        }
        return sql + " AND c.id = :categoryId";
    }

    private Map<String, Object> params(final String name, final CategoryId categoryId) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "%" + name + "%");
        param.put("categoryId", categoryId.getValue());
        return param;
    }
}
