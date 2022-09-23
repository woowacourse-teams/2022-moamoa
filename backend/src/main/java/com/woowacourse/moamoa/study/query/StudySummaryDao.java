package com.woowacourse.moamoa.study.query;

import com.woowacourse.moamoa.study.query.data.StudySummaryData;
import com.woowacourse.moamoa.tag.domain.CategoryName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.RowMapper;
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
        final String status = resultSet.getString("recruitment_status");

        return new StudySummaryData(id, title, excerpt, thumbnail, status);
    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Slice<StudySummaryData> searchBy(final String title, final SearchingTags searchingTags, final Pageable pageable) {
        final List<StudySummaryData> data = jdbcTemplate
                .query(sql(searchingTags, title), params(title, searchingTags, pageable), STUDY_ROW_MAPPER);
        return new SliceImpl<>(getCurrentPageStudies(data, pageable), pageable, hasNext(data, pageable));

    }

    private String sql(final SearchingTags searchingTags, final String title) {
        return "SELECT study.id, study.title, study.excerpt, study.thumbnail, study.recruitment_status, study.created_at "
                + "FROM study "
                + joinTableClause(searchingTags)
                + joinTitleClause(title)
                + filtersInQueryClause(searchingTags)
                + "GROUP BY study.id "
                + "ORDER BY study.created_at DESC "
                + "LIMIT :limit OFFSET :offset ";
    }

    private String joinTitleClause(final String title) {
        if (title.isBlank()) {
            return "";
        }
        return "WHERE UPPER(study.title) LIKE UPPER(:title) ESCAPE '\' ";
    }

    private String joinTableClause(final SearchingTags searchingTags) {
        String sql = "JOIN study_tag {}_study_tag ON study.id = {}_study_tag.study_id "
                + "JOIN tag {}_tag ON {}_study_tag.tag_id = {}_tag.id "
                + "JOIN category {}_category ON {}_tag.category_id = {}_category.id AND {}_category.name = '{}' ";

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
