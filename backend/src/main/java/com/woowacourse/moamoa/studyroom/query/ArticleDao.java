package com.woowacourse.moamoa.studyroom.query;

import com.woowacourse.moamoa.studyroom.domain.ArticleType;
import com.woowacourse.moamoa.studyroom.query.data.ArticleData;
import com.woowacourse.moamoa.member.query.data.MemberData;
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

        final long githubId = rs.getLong("member.github_id");
        final String username = rs.getString("member.username");
        final String imageUrl = rs.getString("member.image_url");
        final String profileUrl = rs.getString("member.profile_url");
        MemberData memberData = new MemberData(githubId, username, imageUrl, profileUrl);

        return new ArticleData(id, memberData, title, content, createdDate, lastModifiedDate);
    };
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public ArticleDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<ArticleData> getById(final Long articleId, ArticleType type) {
        final String sql = "SELECT {}.id as article_id, {}.title as article_title, {}.content as article_content, "
                + "{}.created_date as article_created_date, {}.last_modified_date as article_last_modified_date, "
                + "member.github_id, member.username, member.image_url, member.profile_url "
                + "FROM {} JOIN member ON {}.author_id = member.id "
                + "WHERE {}.id = :{}Id";

        final Map<String, Long> params = Map.of(nameOf(type) + "Id", articleId);
        return namedParameterJdbcTemplate.query(sql.replaceAll("\\{\\}", nameOf(type)), params, ROW_MAPPER).stream().findAny();
    }

    public Page<ArticleData> getAllByStudyId(final Long studyId, final Pageable pageable, ArticleType type) {
        final List<ArticleData> content = getContent(studyId, pageable, type);
        final int totalCount = getTotalCount(studyId, type);
        return new PageImpl<>(content, pageable, totalCount);
    }

    private List<ArticleData> getContent(final Long studyId, final Pageable pageable, ArticleType type) {
        final String sql = "SELECT {}.id as article_id, {}.title as article_title, {}.content as article_content, "
                + "{}.created_date as article_created_date, {}.last_modified_date as article_last_modified_date, "
                + "member.github_id, member.username, member.image_url, member.profile_url "
                + "FROM {} JOIN member ON {}.author_id = member.id "
                + "WHERE {}.study_id = :studyId "
                + "ORDER BY created_date DESC, {}.id DESC "
                + "LIMIT :size OFFSET :offset";

        final Map<String, Object> params = Map.of(
                "studyId", studyId,
                "size", pageable.getPageSize(),
                "offset", pageable.getOffset()
        );

        return namedParameterJdbcTemplate.query(sql.replaceAll("\\{\\}", nameOf(type)), params, ROW_MAPPER);
    }

    private Integer getTotalCount(final Long studyId, ArticleType type) {
        final String sql = "SELECT count({}.id) FROM {} WHERE {}.study_id = :studyId";
        final Map<String, Long> param = Map.of("studyId", studyId);
        return namedParameterJdbcTemplate.queryForObject(sql.replaceAll("\\{\\}", nameOf(type)), param, (rs, rn) -> rs.getInt(1));
    }

    private String nameOf(final ArticleType type) {
        return type.name().toLowerCase();
    }
}
