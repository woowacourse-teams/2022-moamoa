package com.woowacourse.moamoa.studyroom.query;

import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.query.data.StudyData;
import com.woowacourse.moamoa.studyroom.query.data.TempArticleData;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TempArticleDao {

    public static final RowMapper<TempArticleData> ROW_MAPPER = (rs, rn) -> {
        final long id = rs.getLong("article_id");
        final String title = rs.getString("article_title");
        final String content = rs.getString("article_content");
        final LocalDate createdDate = rs.getObject("article_created_date", LocalDate.class);
        final LocalDate lastModifiedDate = rs.getObject("article_last_modified_date", LocalDate.class);

        final long studyId = rs.getLong("study_id");
        final String studyTitle = rs.getString("study_title");
        final StudyData studyData = new StudyData(studyId, studyTitle);
        return new TempArticleData(id, studyData, title, content, createdDate, lastModifiedDate);
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public TempArticleDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<TempArticleData> getById(final Long articleId, final ArticleType type) {
        final String sql = "SELECT temp.id as article_id, temp.title as article_title, temp.content as article_content, "
                + "temp.created_date as article_created_date, temp.last_modified_date as article_last_modified_date, "
                + "s.id as study_id, s.title as study_title "
                + "FROM temp_article as temp "
                + "JOIN study as s ON temp.study_id = s.id "
                + "WHERE temp.id = :articleId AND temp.type = :type";

        final Map<String, Object> params = Map.of(
                "articleId", articleId,
                "type", type.name()
        );
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER).stream().findAny();
    }

    public Page<TempArticleData> getAll(final Long authorId, final ArticleType type, final Pageable pageable) {
        final List<TempArticleData> contents = getContents(authorId, type, pageable);
        final long totalCount = getTotalCount(authorId, type);
        return new PageImpl<>(contents, pageable, totalCount);
    }

    private List<TempArticleData> getContents(final Long authorId, final ArticleType type,
                                              final Pageable pageable) {
        final String sql = "SELECT temp.id as article_id, temp.title as article_title, temp.content as article_content, "
                + "temp.created_date as article_created_date, temp.last_modified_date as article_last_modified_date, "
                + "s.id as study_id, s.title as study_title "
                + "FROM temp_article as temp "
                + "JOIN study as s ON temp.study_id = s.id "
                + "WHERE temp.author_id = :authorId AND temp.type = :type "
                + "ORDER BY temp.id desc "
                + "LIMIT :size OFFSET :offset";

        final Map<String, Object> params = Map.of(
                "authorId", authorId,
                "size", pageable.getPageSize(),
                "offset", pageable.getOffset(),
                "type", type.name()
        );
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    private Long getTotalCount(final Long authorId, final ArticleType type) {
        final String sql = "SELECT COUNT(temp_article.id) FROM temp_article "
                + "WHERE temp_article.author_id = :authorId "
                + "AND temp_article.type = :type";
        final Map<String, Object> params = Map.of(
                "authorId", authorId,
                "type", type.name()
        );
        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rn) -> rs.getLong(1));
    }


}
