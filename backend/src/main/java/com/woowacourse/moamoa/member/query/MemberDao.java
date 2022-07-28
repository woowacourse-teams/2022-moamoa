package com.woowacourse.moamoa.member.query;

import com.woowacourse.moamoa.member.query.data.MemberData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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

    public List<MemberData> findMembersByStudyId(Long studyId) {
        String sql = "SELECT github_id, username, image_url, profile_url "
                + "FROM member JOIN study_member ON member.id = study_member.member_id "
                + "WHERE study_member.study_id = :id";
        return jdbcTemplate.query(sql, Map.of("id", studyId), ROW_MAPPER);
    }

    public List<MemberData> findOwnerByStudyIds(List<Long> studyIds) {
        List<String> ids = studyIds.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);

        String sql = "SELECT member.github_id, member.username, member.image_url, member.profile_url "
                + "FROM study JOIN member ON member.id = study.owner_id "
                + "WHERE study.id IN (:ids)";

        return jdbcTemplate.query(sql, parameters, ROW_MAPPER);
    }

    public boolean isExistByGithubId(Long id) {
        final String sql = "SELECT EXISTS(SELECT github_id FROM member where github_id = :id) ";
        return jdbcTemplate.queryForObject(sql, Map.of("id", id), Boolean.class);
    }
}
