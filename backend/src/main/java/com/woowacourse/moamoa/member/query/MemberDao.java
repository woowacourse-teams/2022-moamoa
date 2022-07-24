package com.woowacourse.moamoa.member.query;

import com.woowacourse.moamoa.member.query.data.MemberData;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private static final RowMapper<MemberData> ROW_MAPPER = (rs, rn) -> {
        Long githubId = rs.getLong("github_id");
        String username = rs.getString("username");
        String imageUrl = rs.getString("image_url");
        String profileUrl = rs.getString("profile_url");
        return new MemberData(githubId, username, imageUrl, profileUrl);
    };

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberData> getParticipantsBy(Long studyId) {
        String sql = "SELECT github_id, username, image_url, profile_url "
                + "FROM member JOIN study_member ON member.id = study_member.member_id "
                + "WHERE study_member.study_id = ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, studyId);
    }
}
