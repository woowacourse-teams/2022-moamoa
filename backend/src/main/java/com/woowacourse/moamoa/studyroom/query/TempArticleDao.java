package com.woowacourse.moamoa.studyroom.query;

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

        return new TempArticleData(id, title, content, createdDate, lastModifiedDate);
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public TempArticleDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<TempArticleData> getById(final Long articleId) {
        final String sql = "SELECT temp.id as article_id, temp.title as article_title, temp.content as article_content, "
                + "temp.created_date as article_created_date, temp.last_modified_date as article_last_modified_date "
                + "FROM temp_article as temp "
                + "WHERE temp.id = :articleId";

        final Map<String, Object> params = Map.of("articleId", articleId);
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER).stream().findAny();
    }

    public Page<TempArticleData> getAll(final Long authorId, final Long studyId, final Pageable pageable) {
        final List<TempArticleData> contents = getContents(authorId, studyId, pageable);
        final long totalCount = getTotalCount(authorId, studyId);

        return new PageImpl<>(contents, pageable, totalCount);
    }

    private List<TempArticleData> getContents(final Long authorId, final Long studyId, final Pageable pageable) {
        final String sql = "SELECT temp.id as article_id, temp.title as article_title, temp.content as article_content, "
                + "temp.created_date as article_created_date, temp.last_modified_date as article_last_modified_date "
                + "FROM temp_article as temp "
                + "WHERE temp.study_id = :studyId AND temp.author_id = :authorId "
                + "ORDER BY temp.id desc "
                + "LIMIT :size OFFSET :offset";

        final Map<String, Object> params = Map.of(
                "studyId", studyId,
                "authorId", authorId,
                "size", pageable.getPageSize(),
                "offset", pageable.getOffset()
        );
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    private Long getTotalCount(final Long authorId, final Long studyId) {
        final String sql = "SELECT COUNT(temp_article.id) FROM temp_article "
                + "WHERE temp_article.study_id = :studyId AND temp_article.author_id = :authorId ";
        final Map<String, Object> params = Map.of(
                "studyId", studyId,
                "authorId", authorId
        );
        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rn) -> rs.getLong(1));
    }


}
