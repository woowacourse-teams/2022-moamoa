package com.woowacourse.moamoa.member.query;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.member.query.data.MemberFullData;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private static final RowMapper<MemberFullData> MEMBER_FULL_DATA_ROW_MAPPER = createMemberFullDataRowMapper();

    private static final RowMapper<MemberData> MEMBER_DATA_ROW_MAPPER = createMemberDataRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MemberDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MemberFullData> findMembersByStudyId(final Long studyId) {
        final String sql = "SELECT member.id, github_id, username, image_url, profile_url, "
                + "study_member.participation_date as participation_date, "
                + countStudyNumber()
                + "FROM member JOIN study_member ON member.id = study_member.member_id "
                + "JOIN study ON study_member.study_id = study.id "
                + "WHERE study_member.study_id = :id";

        return jdbcTemplate.query(sql, Map.of("id", studyId), MEMBER_FULL_DATA_ROW_MAPPER);
    }

    private String countStudyNumber() {
        return countOwnerStudy() + " + " + countParticipationStudy();
    }

    private String countParticipationStudy() {
        return "((SELECT count(case when (study_member.member_id = member.id) then 1 end) "
                + "FROM study JOIN study_member ON study.id = study_member.study_id) ";
    }

    private String countOwnerStudy() {
        return "(SELECT count(case when (study.owner_id = member.id) then 1 end) "
                + "FROM study)) as number_of_study ";
    }

    public Optional<MemberData> findByGithubId(final Long githubId) {
        try {
            final String sql = "SELECT github_id, username, image_url, profile_url "
                    + "FROM member "
                    + "WHERE member.github_id = :id";
            final MemberData data = jdbcTemplate.queryForObject(sql, Map.of("id", githubId), MEMBER_DATA_ROW_MAPPER);
            return Optional.of(data);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static RowMapper<MemberFullData> createMemberFullDataRowMapper() {
        return (resultSet, resultNumber) -> {
            Long githubId = resultSet.getLong("github_id");
            String username = resultSet.getString("username");
            String imageUrl = resultSet.getString("image_url");
            String profileUrl = resultSet.getString("profile_url");
            LocalDate participationDate = resultSet.getDate("participation_date").toLocalDate();
            int numberOfStudy = resultSet.getInt("number_of_study");
            return new MemberFullData(githubId, username, imageUrl, profileUrl, participationDate, numberOfStudy);
        };
    }

    private static RowMapper<MemberData> createMemberDataRowMapper() {
        return (resultSet, resultNumber) -> {
            Long githubId = resultSet.getLong("github_id");
            String username = resultSet.getString("username");
            String imageUrl = resultSet.getString("image_url");
            String profileUrl = resultSet.getString("profile_url");
            return new MemberData(githubId, username, imageUrl, profileUrl);
        };
    }
}
