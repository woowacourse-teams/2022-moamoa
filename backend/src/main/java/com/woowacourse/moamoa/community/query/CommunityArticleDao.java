package com.woowacourse.moamoa.community.query;

import com.woowacourse.moamoa.community.query.data.CommunityArticleData;
import com.woowacourse.moamoa.member.query.data.MemberData;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommunityArticleDao {

    public static final RowMapper<CommunityArticleData> ROW_MAPPER = (rs, rn) -> {
        final long id = rs.getLong("article.id");
        final String title = rs.getString("article.title");
        final String content = rs.getString("article.content");
        final LocalDate createdDate = rs.getObject("article.created_date", LocalDate.class);
        final LocalDate lastModifiedDate = rs.getObject("article.last_modified_date", LocalDate.class);

        final long githubId = rs.getLong("member.github_id");
        final String username = rs.getString("member.username");
        final String imageUrl = rs.getString("member.image_url");
        final String profileUrl = rs.getString("member.profile_url");
        MemberData memberData = new MemberData(githubId, username, imageUrl, profileUrl);

        return new CommunityArticleData(id, memberData, title, content, createdDate, lastModifiedDate);
    };
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public CommunityArticleDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<CommunityArticleData> getById(final Long articleId) {
        final String sql = "SELECT article.id, article.title, article.content, "
                + "article.created_date, article.last_modified_date, "
                + "member.github_id, member.username, member.image_url, member.profile_url "
                + "FROM article JOIN member ON article.author_id = member.id "
                + "WHERE article.id = :articleId";
        final Map<String, Long> params = Map.of("articleId", articleId);
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER).stream().findAny();
    }
}
