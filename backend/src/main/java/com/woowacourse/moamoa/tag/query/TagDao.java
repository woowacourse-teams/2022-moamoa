package com.woowacourse.moamoa.tag.query;

import com.woowacourse.moamoa.tag.query.request.CategoryIdRequest;
import com.woowacourse.moamoa.tag.query.response.CategoryData;
import com.woowacourse.moamoa.tag.query.response.TagData;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

    private static final ResultSetExtractor<Map<Long, List<TagSummaryData>>> STUDY_WITH_TAG_ROW_MAPPER = rs -> {
        final Map<Long, List<TagSummaryData>> result = new LinkedHashMap<>();

        while (rs.next()){
            final Long studyId = rs.getLong("study_id");

            if (!result.containsKey(studyId)) {
                result.put(studyId, new ArrayList<>());
            }

            final Long tagId = rs.getLong("tag_id");
            final String tagName = rs.getString("tag_name");
            final TagSummaryData tagSummaryData = new TagSummaryData(tagId, tagName);

            final List<TagSummaryData> findTagSummaryData = result.get(studyId);
            findTagSummaryData.add(tagSummaryData);
        }
        return result;
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

    public Map<Long, List<TagSummaryData>> findTagsByStudyIds(final List<Long> studyIds) {
        if (studyIds.isEmpty()) {
            return new HashMap<>();
        }

        final String sql = "SELECT tag.id tag_id, tag.name tag_name, study.id study_id "
                + "FROM tag "
                + "JOIN study_tag ON tag.id = study_tag.tag_id "
                + "JOIN study ON study_tag.study_id = study.id "
                + "WHERE study.id IN (:ids)";
        final MapSqlParameterSource params = new MapSqlParameterSource("ids", studyIds);

        try {
            return jdbcTemplate.query(sql, params, STUDY_WITH_TAG_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Map.of();
        }
    }

    public List<TagData> searchByShortNameAndCategoryId(final String tagShortName, final CategoryIdRequest categoryId) {
        final String sql = sqlForSearchByCategoryAndShortName(categoryId);
        final Map<String, Object> params = paramsForSearchByCategoryAndShortName(tagShortName, categoryId);
        return jdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    private String sqlForSearchByCategoryAndShortName(final CategoryIdRequest categoryId) {
        String sql = "SELECT t.id as tag_id, t.name as tag_name, t.description as tag_description, "
                + "c.id as category_id, c.name as category_name "
                + "FROM tag as t JOIN category as c ON t.category_id = c.id "
                + "WHERE UPPER(t.name) LIKE UPPER(:name)";

        if (categoryId.isEmpty()) {
            return sql;
        }
        return sql + " AND c.id = :categoryId";
    }

    private Map<String, Object> paramsForSearchByCategoryAndShortName(final String name, final CategoryIdRequest categoryId) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "%" + name + "%");
        param.put("categoryId", categoryId.getValue());
        return param;
    }
}
