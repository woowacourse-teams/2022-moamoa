package com.woowacourse.moamoa.study.query;

import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import com.woowacourse.moamoa.tag.domain.CategoryName;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudySummaryDao {

    private static final RowMapper<StudySummaryData> STUDY_ROW_MAPPER = (resultSet, rowNum) -> {
        final Long id = resultSet.getLong("id");
        final String title = resultSet.getString("title");
        final String excerpt = resultSet.getString("excerpt");
        final String thumbnail = resultSet.getString("thumbnail");
        final String status = resultSet.getString("recruit_status");

        return new StudySummaryData(id, title, excerpt, thumbnail, status);
    };

    private static final RowMapper<Map<Long, List<TagSummaryData>>> STUDY_WITH_TAG_ROW_MAPPER = (rs, rn) -> {
        final Map<Long, List<TagSummaryData>> result = new LinkedHashMap<>();

        for (int idx = 0; idx < rs.getRow(); idx++) {
            final Long studyId = rs.getLong("study_id");

            if (!result.containsKey(studyId)) {
                result.put(studyId, new ArrayList<>());
            }

            final Long tagId = rs.getLong("tag_id");
            final String tagName = rs.getString("tag_name");
            final TagSummaryData tagSummaryData = new TagSummaryData(tagId, tagName);

            final List<TagSummaryData> findTagSummaryData = result.get(studyId);
            findTagSummaryData.add(tagSummaryData);
            rs.next();
        }
        return result;
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Slice<StudySummaryData> searchBy(final String title, final SearchingTags searchingTags, final Pageable pageable) {
        final List<StudySummaryData> data = jdbcTemplate
                .query(sql(searchingTags), params(title, searchingTags, pageable), STUDY_ROW_MAPPER);
        return new SliceImpl<>(getCurrentPageStudies(data, pageable), pageable, hasNext(data, pageable));

    }

    public Map<Long, List<TagSummaryData>> findStudyWithTags(final List<Long> studyIds) {
        final List<String> ids = studyIds.stream()
                .map(Objects::toString)
                .collect(Collectors.toList());
        final MapSqlParameterSource params = new MapSqlParameterSource("ids", ids);

        final String sql = "SELECT tag.id tag_id, tag.name tag_name, study.id study_id "
                + "FROM tag "
                + "JOIN study_tag ON tag.id = study_tag.tag_id "
                + "JOIN study ON study_tag.study_id = study.id "
                + "WHERE study.id IN (:ids)";

        try {
            return jdbcTemplate.queryForObject(sql, params, STUDY_WITH_TAG_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Map.of();
        }
    }

    private String sql(final SearchingTags searchingTags) {
        return "SELECT study.id, study.title, study.excerpt, study.thumbnail, study.recruit_status "
                + "FROM study "
                + joinTableClause(searchingTags)
                + "WHERE UPPER(study.title) LIKE UPPER(:title) ESCAPE '\' "
                + filtersInQueryClause(searchingTags)
                + "GROUP BY study.id LIMIT :limit OFFSET :offset";
    }

    private String joinTableClause(final SearchingTags searchingTags) {
        String sql = "JOIN study_tag {}_study_tag ON study.id = {}_study_tag.study_id "
                + "JOIN tag {}_tag ON {}_study_tag.tag_id = {}_tag.id "
                + "JOIN category {}_category ON {}_tag.category_id = {}_category.id AND {}_category.name = '{}'";

        return Stream.of(CategoryName.values())
                .filter(searchingTags::hasBy)
                .map(name -> sql.replaceAll("\\{\\}", name.name().toLowerCase()))
                .collect(Collectors.joining());
    }

    private String filtersInQueryClause(final SearchingTags searchingTags) {
        String sql = "AND {}_tag.id IN (:{}) ";

        return Stream.of(CategoryName.values())
                .filter(searchingTags::hasBy)
                .map(name -> sql.replaceAll("\\{\\}", name.name().toLowerCase()))
                .collect(Collectors.joining());
    }

    private Map<String, Object> params(final String title, final SearchingTags searchingTags,
                                       final Pageable pageable) {
        final Map<String, Object> tagIds = Stream.of(CategoryName.values())
                .collect(Collectors.toMap(name -> name.name().toLowerCase(), searchingTags::getTagIdsBy));

        Map<String, Object> param = new HashMap<>();
        param.put("title", "%" + title + "%");
        param.put("limit", pageable.getPageSize() + 1);
        param.put("offset", pageable.getOffset());
        param.putAll(tagIds);
        return param;
    }

    private List<StudySummaryData> getCurrentPageStudies(final List<StudySummaryData> studies, final Pageable pageable) {
        if (hasNext(studies, pageable)) {
            return studies.subList(0, studies.size() - 1);
        }
        return studies;
    }

    private boolean hasNext(final List<StudySummaryData> studies, final Pageable pageable) {
        return studies.size() > pageable.getPageSize();
    }

}
