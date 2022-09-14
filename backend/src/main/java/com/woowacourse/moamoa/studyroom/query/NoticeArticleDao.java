package com.woowacourse.moamoa.studyroom.query;

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
public class NoticeArticleDao {

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


    public NoticeArticleDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<ArticleData> getById(final Long articleId) {
        final String sql = "SELECT notice.id as article_id, notice.title as article_title, notice.content as article_content, "
                + "notice.created_date as article_created_date, notice.last_modified_date as article_last_modified_date, "
                + "member.github_id, member.username, member.image_url, member.profile_url "
                + "FROM notice JOIN member ON notice.author_id = member.id "
                + "WHERE notice.id = :noticeId and notice.deleted = false";

        final Map<String, Long> params = Map.of("noticeId", articleId);
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER).stream().findAny();
    }

    public Page<ArticleData> getAllByStudyId(final Long studyId, final Pageable pageable) {
        final List<ArticleData> content = getContent(studyId, pageable);
        final int totalCount = getTotalCount(studyId);
        return new PageImpl<>(content, pageable, totalCount);
    }

    private List<ArticleData> getContent(final Long studyId, final Pageable pageable) {
        final String sql = "SELECT notice.id as article_id, notice.title as article_title, notice.content as article_content, "
                + "notice.created_date as article_created_date, notice.last_modified_date as article_last_modified_date, "
                + "member.github_id, member.username, member.image_url, member.profile_url "
                + "FROM notice JOIN member ON notice.author_id = member.id "
                + "WHERE notice.study_id = :studyId and notice.deleted = false "
                + "ORDER BY created_date DESC, notice.id DESC "
                + "LIMIT :size OFFSET :offset";

        final Map<String, Object> params = Map.of(
                "studyId", studyId,
                "size", pageable.getPageSize(),
                "offset", pageable.getOffset()
        );

        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    private Integer getTotalCount(final Long studyId) {
        final String sql = "SELECT count(notice.id) FROM notice WHERE notice.study_id = :studyId and notice.deleted = false";
        final Map<String, Long> param = Map.of("studyId", studyId);
        return namedParameterJdbcTemplate.queryForObject(sql, param, (rs, rn) -> rs.getInt(1));
    }
}
