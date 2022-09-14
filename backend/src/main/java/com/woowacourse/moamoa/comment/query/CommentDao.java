package com.woowacourse.moamoa.comment.query;

import com.woowacourse.moamoa.comment.query.data.CommentData;
import com.woowacourse.moamoa.member.query.data.MemberData;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<CommentData> findAllByArticleId(final Long communityId, final Pageable pageable) {
        final long offset = pageable.getOffset();
        final int pageSize = pageable.getPageSize();
        String sql = "SELECT comment.id, comment.content, comment.created_date, comment.last_modified_date, "
                + "member.github_id, member.username, member.image_url, member.profile_url "
                + "FROM comment JOIN member ON comment.member_id = member.id "
                + "WHERE comment.community_id = :communityId "
                + "ORDER BY comment.created_date DESC, comment.id DESC LIMIT :limit OFFSET :offset";

        return namedParameterJdbcTemplate.query(sql,
                Map.of("communityId", communityId, "limit", pageSize, "offset", offset), rowMapper());
    }

    private RowMapper<CommentData> rowMapper() {
        return (rs, rn) -> {
            final Long commentId = rs.getLong("id");
            final LocalDate createdDate = rs.getObject("created_date", LocalDate.class);
            final LocalDate lastModifiedDate = rs.getObject("last_modified_date", LocalDate.class);
            final String content = rs.getString("content");

            final Long githubId = rs.getLong("github_id");
            final String username = rs.getString("username");
            final String imageUrl = rs.getString("image_url");
            final String profileUrl = rs.getString("profile_url");

            return new CommentData(commentId, new MemberData(githubId, username, imageUrl, profileUrl),
                    createdDate, lastModifiedDate, content);
        };
    }
}
