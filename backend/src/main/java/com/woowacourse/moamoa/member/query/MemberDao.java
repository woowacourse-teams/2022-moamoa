package com.woowacourse.moamoa.member.query;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.member.query.data.ParticipatingMemberData;
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

    private static final RowMapper<ParticipatingMemberData> MEMBER_FULL_DATA_ROW_MAPPER = createMemberFullDataRowMapper();

    private static final RowMapper<MemberData> MEMBER_DATA_ROW_MAPPER = createMemberDataRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MemberDao(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ParticipatingMemberData> findMembersByStudyId(final Long studyId) {
        final String sql = "SELECT member.id, github_id, username, image_url, profile_url, "
                + "study_member.participation_date as participation_date, "
                + countStudyNumber()
                + "FROM member JOIN study_member ON member.id = study_member.member_id "
                + "JOIN study ON study_member.study_id = study.id "
                + "WHERE study_member.study_id = :id";

        return jdbcTemplate.query(sql, Map.of("id", studyId), MEMBER_FULL_DATA_ROW_MAPPER);
    }

    private String countStudyNumber() {
        return countParticipationStudy() + " + " + countOwnerStudy();
    }

    private String countParticipationStudy() {
        return "((SELECT COUNT(*) FROM study_member WHERE study_member.member_id = member.id) ";
    }

    private String countOwnerStudy() {
        return "(SELECT COUNT(*) FROM study WHERE study.owner_id = member.id)) as number_of_study ";
    }

    public Optional<MemberData> findByMemberId(final Long memberId) {
        try {
            final String sql = "SELECT id, username, image_url, profile_url "
                    + "FROM member "
                    + "WHERE member.id = :id";
            final MemberData data = jdbcTemplate.queryForObject(sql, Map.of("id", memberId), MEMBER_DATA_ROW_MAPPER);
            return Optional.of(data);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static RowMapper<ParticipatingMemberData> createMemberFullDataRowMapper() {
        return (resultSet, resultNumber) -> {
            Long githubId = resultSet.getLong("github_id");
            String username = resultSet.getString("username");
            String imageUrl = resultSet.getString("image_url");
            String profileUrl = resultSet.getString("profile_url");
            LocalDate participationDate = resultSet.getDate("participation_date").toLocalDate();
            int numberOfStudy = resultSet.getInt("number_of_study");
            return new ParticipatingMemberData(githubId, username, imageUrl, profileUrl, participationDate, numberOfStudy);
        };
    }

    private static RowMapper<MemberData> createMemberDataRowMapper() {
        return (resultSet, resultNumber) -> {
            Long id = resultSet.getLong("id");
            String username = resultSet.getString("username");
            String imageUrl = resultSet.getString("image_url");
            String profileUrl = resultSet.getString("profile_url");
            return new MemberData(id, username, imageUrl, profileUrl);
        };
    }
}
