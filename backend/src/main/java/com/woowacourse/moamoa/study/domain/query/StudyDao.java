package com.woowacourse.moamoa.study.domain.query;

import com.woowacourse.moamoa.study.service.response.StudiesResponse;
import com.woowacourse.moamoa.study.service.response.StudyResponse;
import com.woowacourse.moamoa.tag.domain.CategoryName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudyDao {

    private static final RowMapper<StudyResponse> STUDY_ROW_MAPPER = createStudyRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StudiesResponse searchBy(final StudySearchCondition condition, final Pageable pageable) {
        final List<StudyResponse> studies = jdbcTemplate
                .query(sql(condition), params(condition, pageable), STUDY_ROW_MAPPER);
        return new StudiesResponse(getCurrentPageStudies(studies, pageable), hasNext(studies, pageable));
    }

    private String sql(final StudySearchCondition condition) {
        return "SELECT s.id, s.title, s.excerpt, s.thumbnail, s.status "
                + "FROM study s "
                + joinTableClause(condition)
                + "WHERE UPPER(s.title) LIKE UPPER(:title) ESCAPE '\' "
                + filtersInQueryClause(condition)
                + "GROUP BY s.id LIMIT :limit OFFSET :offset";
    }

    private String joinTableClause(final StudySearchCondition condition) {
        String sql = "JOIN study_tag {}_st ON s.id = {}_st.study_id "
                + "JOIN tag {}_t ON {}_st.tag_id = {}_t.id "
                + "JOIN category {}_c ON {}_t.category_id = {}_c.id AND {}_c.name = '{}' ";

        return Stream.of(CategoryName.values())
                .filter(condition::hasBy)
                .map(name -> sql.replaceAll("\\{\\}", name.name()))
                .collect(Collectors.joining());
    }

    private String filtersInQueryClause(final StudySearchCondition condition) {
        String sql = "AND {}_t.id IN (:{}) ";

        return Stream.of(CategoryName.values())
                .filter(condition::hasBy)
                .map(name -> sql.replaceAll("\\{\\}", name.name()))
                .collect(Collectors.joining());
    }

    private Map<String, Object> params(final StudySearchCondition condition,
                                       final Pageable pageable) {
        final Map<String, Object> filterIds = Stream.of(CategoryName.values())
                .collect(Collectors.toMap(Enum::name, condition::getTagIdsBy));

        Map<String, Object> param = new HashMap<>();
        param.put("title", "%" + condition.getTitle() + "%");
        param.put("limit", pageable.getPageSize() + 1);
        param.put("offset", pageable.getOffset());
        param.putAll(filterIds);
        return param;
    }

    private List<StudyResponse> getCurrentPageStudies(final List<StudyResponse> studies, final Pageable pageable) {
        if (hasNext(studies, pageable)) {
            return studies.subList(0, studies.size() - 1);
        }
        return studies;
    }

    private boolean hasNext(final List<StudyResponse> studies, final Pageable pageable) {
        return studies.size() > pageable.getPageSize();
    }

    private static RowMapper<StudyResponse> createStudyRowMapper() {
        return (resultSet, rowNum) -> {
            final long id = resultSet.getLong("id");

            final String title = resultSet.getString("title");
            final String excerpt = resultSet.getString("excerpt");
            final String thumbnail = resultSet.getString("thumbnail");
            final String status = resultSet.getString("status");

            return new StudyResponse(id, title, excerpt, thumbnail, status);
        };
    }
}
