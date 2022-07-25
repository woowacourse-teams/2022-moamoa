package com.woowacourse.moamoa.study.query;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.query.data.StudyDetailsData;
import com.woowacourse.moamoa.study.query.data.StudyDetailsDataBuilder;
import com.woowacourse.moamoa.study.service.exception.StudyNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudyDetailsDao {

    private final JdbcTemplate jdbcTemplate;

    public StudyDetailsData findBy(Long studyId) {
        String sql = "SELECT study.id, title, excerpt, thumbnail, status, description, current_member_count, "
                + "max_member_count, created_at, enrollment_end_date, start_date, end_date, owner_id, "
                + "member.github_id as owner_github_id, member.username as owner_username, "
                + "member.image_url as owner_image_url, member.profile_url as owner_profile_url "
                + "FROM study JOIN member ON study.owner_id = member.id "
                + "WHERE study.id = ?";
        return jdbcTemplate.query(sql, new StudyDetailsDataExtractor(), studyId);
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
            String status = rs.getString("status");
            String description = rs.getString("description");
            LocalDate createdAt = rs.getObject("created_at", LocalDate.class);

            builder.id(id).title(title).excerpt(excerpt)
                    .thumbnail(thumbnail).status(status)
                    .description(description).createdAt(createdAt);
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

            Long githubId = rs.getLong("owner_github_id");
            String username = rs.getString("owner_username");
            String imageUrl = rs.getString("owner_image_url");
            String profileUrl = rs.getString("owner_profile_url");

            builder.currentMemberCount(currentMaxCount)
                    .maxMemberCount(maxMemberCount)
                    .owner(new MemberData(githubId, username, imageUrl, profileUrl));
        }
    }
}

