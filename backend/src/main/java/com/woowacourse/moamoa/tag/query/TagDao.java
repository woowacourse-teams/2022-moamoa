package com.woowacourse.moamoa.tag.query;

import com.woowacourse.moamoa.tag.query.response.CategoryData;
import com.woowacourse.moamoa.tag.query.response.TagData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TagDao {

    public static final RowMapper<TagData> ROW_MAPPER = (rs, rn) -> {
        final long tagId = rs.getLong("tag_id");
        final String tagName = rs.getString("tag_name");
        final String tagDescription = rs.getString("tag_description");
        final long categoryId = rs.getLong("category_id");
        final String categoryName = rs.getString("category_name");

        return new TagData(tagId, tagName, tagDescription,
                new CategoryData(categoryId, categoryName.toLowerCase()));
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<TagData> findTagsByStudyId(Long studyId) {
        String sql = "SELECT t.id as tag_id, t.name as tag_name, t.description as tag_description, "
                + "c.id as category_id, c.name as category_name "
                + "FROM tag as t JOIN category as c ON t.category_id = c.id "
                + "JOIN study_tag as st ON t.id = st.tag_id "
                + "WHERE st.study_id = :studyId";
        return jdbcTemplate.query(sql, Map.of("studyId", studyId), ROW_MAPPER);
    }

    public List<TagData> searchByShortNameAndCategoryId(final String tagShortName, final Optional<Long> categoryId) {
        final String sql = sqlForSearchByCategoryAndShortName(categoryId);
        final Map<String, Object> params = paramsForSearchByCategoryAndShortName(tagShortName, categoryId);
        return jdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    private String sqlForSearchByCategoryAndShortName(final Optional<Long> categoryId) {
        String sql = "SELECT t.id as tag_id, t.name as tag_name, t.description as tag_description, "
                + "c.id as category_id, c.name as category_name "
                + "FROM tag as t JOIN category as c ON t.category_id = c.id "
                + "WHERE UPPER(t.name) LIKE UPPER(:name)";

        if (categoryId.isEmpty()) {
            return sql;
        }
        return sql + " AND c.id = :categoryId";
    }

    private Map<String, Object> paramsForSearchByCategoryAndShortName(final String name, final Optional<Long> categoryId) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "%" + name + "%");
        param.put("categoryId", categoryId.orElse(null));
        return param;
    }
}
