package com.woowacourse.moamoa.study.query;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.query.data.StudyData;
import com.woowacourse.moamoa.study.query.data.StudyData.StudyDataBuilder;
import com.woowacourse.moamoa.study.service.exception.StudyNotExistException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class StudyDao {

    private final JdbcTemplate jdbcTemplate;

    public StudyDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StudyData getById(Long studyId) {
        String sql = "SELECT study.id, title, excerpt, thumbnail, status, description, current_member_count, "
                + "max_member_count, created_at, enrollment_end_date, start_date, end_date, owner_id, "
                + "member.github_id as owner_github_id, member.username as owner_username, "
                + "member.image_url as owner_image_url, member.profile_url as owner_profile_url "
                + "FROM study JOIN member ON study.owner_id = member.id "
                + "WHERE study.id = ?";
        return jdbcTemplate.query(sql, new StudyContentExtractor(), studyId);
    }

    private static class StudyContentExtractor implements ResultSetExtractor<StudyData> {

        private final StudyDataBuilder builder;

        public StudyContentExtractor() {
            builder = StudyData.builder();
        }

        @Override
        public StudyData extractData(final ResultSet resultSet) throws SQLException, DataAccessException {
            if (resultSet.next()) {
                appendStudyContent(resultSet);
                appendOwner(resultSet);
                return builder.build();
            }
            throw new StudyNotExistException();
        }

        private void appendStudyContent(final ResultSet rs) throws SQLException {
            Long id = rs.getLong("id");
            String title = rs.getString("title");
            String excerpt = rs.getString("excerpt");
            String thumbnail = rs.getString("thumbnail");
            String status = rs.getString("status");
            String description = rs.getString("description");
            Integer currentMaxCount = rs.getInt("current_member_count");
            Integer maxMemberCount = rs.getInt("max_member_count");
            LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
            LocalDateTime enrollmentEndDDate = rs.getObject("enrollment_end_date", LocalDateTime.class);
            LocalDateTime startDate = rs.getObject("start_date", LocalDateTime.class);
            LocalDateTime endDate = rs.getObject("end_date", LocalDateTime.class);
            builder.id(id).title(title).excerpt(excerpt).thumbnail(thumbnail)
                    .status(status).description(description).currentMemberCount(currentMaxCount)
                    .maxMemberCount(maxMemberCount).createdAt(createdAt).enrollmentEndDate(enrollmentEndDDate)
                    .startDate(startDate).endDate(endDate);
        }

        private void appendOwner(final ResultSet resultSet) throws SQLException {
            Long githubId = resultSet.getLong("owner_github_id");
            String username = resultSet.getString("owner_username");
            String imageUrl = resultSet.getString("owner_image_url");
            String profileUrl = resultSet.getString("owner_profile_url");

            builder.owner(new MemberData(githubId, username, imageUrl, profileUrl));
        }
    }
}

