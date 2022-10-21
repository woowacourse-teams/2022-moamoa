package com.woowacourse.moamoa.comment.query;

import com.woowacourse.moamoa.comment.query.data.CommentData;
import com.woowacourse.moamoa.member.query.data.MemberData;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Slice<CommentData> findAllByArticleId(final Long articleId, final Pageable pageable) {
        final long offset = pageable.getOffset();
        final int pageSize = pageable.getPageSize();
        String sql = "SELECT comment.id, comment.content, comment.created_date, comment.last_modified_date, "
                + "member.id, member.username, member.image_url, member.profile_url "
                + "FROM comment JOIN member ON comment.author_id = member.id "
                + "WHERE comment.article_id = :articleId "
                + "ORDER BY comment.created_date DESC, comment.id DESC LIMIT :limit OFFSET :offset";

        final List<CommentData> comments = namedParameterJdbcTemplate.query(sql,
                Map.of("articleId", articleId, "limit", pageSize + 1, "offset", offset), rowMapper());
        return new SliceImpl<>(getCurrentPageComments(comments, pageable), pageable, hasNext(comments, pageable));
    }

    public long getCommentTotalCount(final Long articleId) {
        final String countSql = "SELECT count(*) as total_count FROM comment "
                + "WHERE comment.article_id = :articleId";

        return namedParameterJdbcTemplate.queryForObject(countSql, Map.of("articleId", articleId),
                (rs, rn) -> rs.getLong("total_count"));
    }

    private List<CommentData> getCurrentPageComments(final List<CommentData> comments, final Pageable pageable) {
        if (hasNext(comments, pageable)) {
            return comments.subList(0, comments.size() - 1);
        }
        return comments;
    }

    private boolean hasNext(final List<CommentData> comments, final Pageable pageable) {
        return comments.size() > pageable.getPageSize();
    }

    private RowMapper<CommentData> rowMapper() {
        return (rs, rn) -> {
            final Long commentId = rs.getLong("id");
            final LocalDate createdDate = rs.getObject("created_date", LocalDate.class);
            final LocalDate lastModifiedDate = rs.getObject("last_modified_date", LocalDate.class);
            final String content = rs.getString("content");

            final Long memberId = rs.getLong("member.id");
            final String username = rs.getString("username");
            final String imageUrl = rs.getString("image_url");
            final String profileUrl = rs.getString("profile_url");

            return new CommentData(commentId, new MemberData(memberId, username, imageUrl, profileUrl),
                    createdDate, lastModifiedDate, content);
        };
    }
}
