package com.woowacourse.moamoa.study.query;

import com.woowacourse.moamoa.member.query.data.OwnerData;
import com.woowacourse.moamoa.study.query.data.StudyDetailsData;
import com.woowacourse.moamoa.study.query.data.StudyDetailsDataBuilder;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudyDetailsDao {

    private final JdbcTemplate jdbcTemplate;

    public Optional<StudyDetailsData> findBy(Long studyId) {
        try {
            String sql =
                    "SELECT study.id, title, excerpt, thumbnail, recruitment_status, description, current_member_count, "
                            + "max_member_count, created_at, enrollment_end_date, start_date, end_date, owner_id, "
                            + "member.id as owner_id, member.username as owner_username, "
                            + "member.image_url as owner_image_url, member.profile_url as owner_profile_url, created_at as participation_date, "
                            + countOfStudy()
                            + "FROM study JOIN member ON study.owner_id = member.id "
                            + "WHERE study.id = ?";
            final StudyDetailsData data = jdbcTemplate.query(sql, new StudyDetailsDataExtractor(), studyId);
            return Optional.of(data);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private String countOfStudy() {
        return "((SELECT count(case when (study_member.member_id = member.id) then 1 end) FROM study JOIN study_member ON study.id = study_member.study_id) "
                + "+ "
                + "(SELECT count(case when (study.owner_id = member.id) then 1 end) FROM study)) as number_of_study ";
    }

    private static class StudyDetailsDataExtractor implements ResultSetExtractor<StudyDetailsData> {

        private final StudyDetailsDataBuilder builder;

        public StudyDetailsDataExtractor() {
            builder = StudyDetailsData.builder();
        }

        @Override
        public StudyDetailsData extractData(final ResultSet resultSet) throws SQLException, DataAccessException {
            if (resultSet.next()) {
                appendStudyContent(resultSet);
                appendStudyPeriod(resultSet);
                appendParticipants(resultSet);
                return builder.build();
            }
            throw new StudyNotFoundException();
        }

        private void appendStudyContent(final ResultSet rs) throws SQLException {
            Long id = rs.getLong("id");
            String title = rs.getString("title");
            String excerpt = rs.getString("excerpt");
            String thumbnail = rs.getString("thumbnail");
            String status = rs.getString("recruitment_status");
            String description = rs.getString("description");
            LocalDate createdDate = rs.getObject("created_at", LocalDate.class);

            builder.id(id).title(title).excerpt(excerpt)
                    .thumbnail(thumbnail).status(status)
                    .description(description).createdDate(createdDate);
        }

        private void appendStudyPeriod(final ResultSet rs) throws SQLException {
            LocalDate enrollmentEndDate = rs.getObject("enrollment_end_date", LocalDate.class);
            LocalDate startDate = rs.getObject("start_date", LocalDate.class);
            LocalDate endDate = rs.getObject("end_date", LocalDate.class);

            builder.enrollmentEndDate(enrollmentEndDate)
                    .startDate(startDate)
                    .endDate(endDate);
        }

        private void appendParticipants(final ResultSet rs) throws SQLException {
            Integer currentMaxCount = rs.getObject("current_member_count", Integer.class);
            Integer maxMemberCount = rs.getObject("max_member_count", Integer.class);

            Long ownerId = rs.getLong("owner_id");
            String username = rs.getString("owner_username");
            String imageUrl = rs.getString("owner_image_url");
            String profileUrl = rs.getString("owner_profile_url");

            int numberOfStudy = rs.getInt("number_of_study");
            LocalDate participationDate = rs.getObject("participation_date", LocalDate.class);

            builder.currentMemberCount(currentMaxCount)
                    .maxMemberCount(maxMemberCount)
                    .owner(new OwnerData(ownerId, username, imageUrl, profileUrl, participationDate, numberOfStudy));
        }
    }
}
