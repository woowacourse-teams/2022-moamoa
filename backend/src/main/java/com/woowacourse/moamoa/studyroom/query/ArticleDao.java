package com.woowacourse.moamoa.studyroom.query;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.studyroom.domain.article.ArticleType;
import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
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
public class ArticleDao {

    public static final RowMapper<ArticleData> ROW_MAPPER = (rs, rn) -> {
        final long id = rs.getLong("article_id");
        final String title = rs.getString("article_title");
        final String content = rs.getString("article_content");
        final LocalDate createdDate = rs.getObject("article_created_date", LocalDate.class);
        final LocalDate lastModifiedDate = rs.getObject("article_last_modified_date", LocalDate.class);

        final long memberId = rs.getLong("member.id");
        final String username = rs.getString("member.username");
        final String imageUrl = rs.getString("member.image_url");
        final String profileUrl = rs.getString("member.profile_url");
        MemberData memberData = new MemberData(memberId, username, imageUrl, profileUrl);

        return new ArticleData(id, memberData, title, content, createdDate, lastModifiedDate);
    };
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public ArticleDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<ArticleData> getById(final Long articleId, final ArticleType type) {
        final String sql = "SELECT article.id as article_id, article.title as article_title, article.content as article_content, "
                + "article.created_date as article_created_date, article.last_modified_date as article_last_modified_date, "
                + "member.id, member.username, member.image_url, member.profile_url "
                + "FROM article JOIN member ON article.author_id = member.id "
                + "WHERE article.id = :articleId and article.deleted = false and article.type = :type ";

        final Map<String, Object> params = Map.of(
                "articleId", articleId,
                "type", type.name()
        );
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER).stream().findAny();
    }

    public Page<ArticleData> getAllByStudyId(final Long studyId, final Pageable pageable, final ArticleType type) {
        final List<ArticleData> content = getContent(studyId, pageable, type);
        final int totalCount = getTotalCount(studyId, type);
        return new PageImpl<>(content, pageable, totalCount);
    }

    private List<ArticleData> getContent(final Long studyId, final Pageable pageable, final ArticleType type) {
        final String sql = "SELECT article.id as article_id, article.title as article_title, article.content as article_content, "
                + "article.created_date as article_created_date, article.last_modified_date as article_last_modified_date, "
                + "member.id, member.username, member.image_url, member.profile_url "
                + "FROM article JOIN member ON article.author_id = member.id "
                + "WHERE article.study_id = :studyId and article.deleted = false and article.type = :type "
                + "ORDER BY created_date DESC, article.id DESC "
                + "LIMIT :size OFFSET :offset";

        final Map<String, Object> params = Map.of(
                "studyId", studyId,
                "size", pageable.getPageSize(),
                "offset", pageable.getOffset(),
                "type", type.name()
        );

        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    private Integer getTotalCount(final Long studyId, final ArticleType type) {
        final String sql = "SELECT count(article.id) "
                + "FROM article "
                + "WHERE article.study_id = :studyId and article.deleted = false and article.type = :type ";

        final Map<String, Object> params = Map.of(
                "studyId", studyId,
                "type", type.name()
        );

        return namedParameterJdbcTemplate.queryForObject(sql, params, (rs, rn) -> rs.getInt(1));
    }
}
