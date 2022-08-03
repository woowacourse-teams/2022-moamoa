package com.woowacourse.moamoa.member.query;

import com.woowacourse.moamoa.member.query.data.MemberData;

import java.util.List;
import java.util.Map;

import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MemberDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberData> findMembersByStudyId(final Long studyId) {
        final String sql = "SELECT github_id, username, image_url, profile_url "
                + "FROM member JOIN study_member ON member.id = study_member.member_id "
                + "WHERE study_member.study_id = :id";
        return jdbcTemplate.query(sql, Map.of("id", studyId), ROW_MAPPER);
    }

    public Optional<MemberData> findByGithubId(final Long githubId) {
        final String sql = "SELECT github_id, username, image_url, profile_url "
                + "FROM member "
                + "WHERE member.github_id = :id";
        final List<MemberData> data = jdbcTemplate.query(sql, Map.of("id", githubId), ROW_MAPPER);

        if (data.size() == 1) {
            return Optional.of(data.get(0));
        }
        return Optional.empty();
    }
}
