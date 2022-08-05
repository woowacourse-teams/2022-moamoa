package com.woowacourse.moamoa.review.query;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.review.query.data.ReviewData;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<ReviewData> findAllByStudyId(final Long studyId) {
        String sql = "SELECT review.id, review.content, review.created_date, review.last_modified_date, "
                + "member.github_id, member.username, member.image_url, member.profile_url "
                + "FROM review JOIN member ON review.member_id = member.id "
                + "WHERE review.deleted = false "
                + "AND review.study_id = :studyId "
                + "ORDER BY review.created_date DESC ";

        return namedParameterJdbcTemplate.query(sql, Map.of("studyId", studyId), rowMapper());
    }

    private RowMapper<ReviewData> rowMapper() {
        return (rs, rn) -> {
            final Long reviewId = rs.getLong("id");
            final String content = rs.getString("content");
            final LocalDate createdDate = rs.getObject("created_date", LocalDate.class);
            final LocalDate lastModifiedDate = rs.getObject("last_modified_date", LocalDate.class);
            final Long githubId = rs.getLong("github_id");
            final String username = rs.getString("username");
            final String imageUrl = rs.getString("image_url");
            final String profileUrl = rs.getString("profile_url");
            return new ReviewData(reviewId, new MemberData(githubId, username, imageUrl, profileUrl),
                    createdDate, lastModifiedDate, content);
        };
    }
}
