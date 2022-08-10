package com.woowacourse.moamoa.community.query;

import com.woowacourse.moamoa.community.query.data.CommunityArticleData;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityArticleDao {

    public static final RowMapper<CommunityArticleData> ROW_MAPPER = (rs, rn) -> {
        return null;
    };
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public CommunityArticleDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<CommunityArticleData> getById(final Long articleId) {
        final String sql = "SELECT id, title, content, author_id, study_id "
                + "FROM article "
                + "WHERE id = :articleId";
        final Map<String, Long> params = Map.of("articleId", articleId);
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER).stream().findAny();
    }
}
