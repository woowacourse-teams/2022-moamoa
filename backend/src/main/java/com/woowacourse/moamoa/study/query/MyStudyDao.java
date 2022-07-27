package com.woowacourse.moamoa.study.query;

import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyStudyDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public static final RowMapper<MyStudySummaryData> ROW_MAPPER = (rs, rn) -> {
        final long id = rs.getLong("id");
        final String title = rs.getString("title");
        final String studyStatus = rs.getString("study_status");
        final int currentMemberCount = rs.getInt("current_member_count");
        final int maxMemberCount = rs.getInt("max_member_count");
        final String startDate = rs.getString("start_date");
        final String endDate = rs.getString("end_date");

        return new MyStudySummaryData(id, title, studyStatus, currentMemberCount, maxMemberCount, startDate, endDate);
    };

    public List<MyStudySummaryData> findMyStudyByGithubId(Long id) {
        String sql = "SELECT study.id, study.title, study.study_status, study.current_member_count, "
                + "study.max_member_count, study.start_date, study.end_date "
                + "FROM member JOIN study_member ON member.id = study_member.member_id "
                + "JOIN study ON study.id = study_member.study_id "
                + "WHERE member.github_id = :id";

        return jdbcTemplate.query(sql, Map.of("id", id), ROW_MAPPER);
    }
}
