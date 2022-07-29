package com.woowacourse.moamoa.study.query;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.domain.StudyStatus;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyStudyDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public static final RowMapper<MyStudySummaryData> MY_STUDY_SUMMARY_ROW_MAPPER = (rs, rn) -> {
        final long id = rs.getLong("id");
        final String title = rs.getString("title");
        final String studyStatus = rs.getString("study_status");
        final int currentMemberCount = rs.getInt("current_member_count");
        final int maxMemberCount = rs.getInt("max_member_count");
        final String startDate = rs.getString("start_date");
        final String endDate = rs.getString("end_date");

        return new MyStudySummaryData(id, title, StudyStatus.find(studyStatus),
                currentMemberCount, maxMemberCount, startDate, endDate);
    };

    private static final RowMapper<Map<Long, Map<MemberData, List<TagSummaryData>>>> OWNER_WITH_TAG_ROW_MAPPER = (rs, rn) -> {
        Map<Long, Map<MemberData, List<TagSummaryData>>> result = new LinkedHashMap<>();

        List<TagSummaryData> taqSummary = new ArrayList<>();
        for (int idx = 0; idx < rs.getRow(); idx++) {
            final Long studyId = rs.getLong("study.id");

            if (!result.containsKey(studyId)) {
                Long githubId = rs.getLong("github_id");
                String username = rs.getString("username");
                String imageUrl = rs.getString("image_url");
                String profileUrl = rs.getString("profile_url");

                taqSummary = new ArrayList<>();
                result.put(studyId, new LinkedHashMap<>());
                result.get(studyId).put(new MemberData(githubId, username, imageUrl, profileUrl), taqSummary);
            }

            final Long tagId = rs.getLong("tag.id");
            final String tagName = rs.getString("tag.name");
            taqSummary.add(new TagSummaryData(tagId, tagName));

            rs.next();
        }
        return result;
    };

    public List<MyStudySummaryData> findMyStudyByGithubId(Long id) {
        String sql = "SELECT study.id, study.title, study.study_status, study.current_member_count, "
                + "study.max_member_count, study.start_date, study.end_date "
                + "FROM member JOIN study_member ON member.id = study_member.member_id "
                + "JOIN study ON study.id = study_member.study_id "
                + "WHERE member.github_id = :id";

        return jdbcTemplate.query(sql, Map.of("id", id), MY_STUDY_SUMMARY_ROW_MAPPER);
    }

    public Map<Long, Map<MemberData, List<TagSummaryData>>> findStudyOwnerWithTags(List<Long> studyIds) {
        List<String> ids = studyIds.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);

        String sql = "SELECT study.id, member.github_id, member.username, member.image_url, member.profile_url, tag.id, tag.name "
                + "FROM study JOIN member ON member.id = study.owner_id "
                + "JOIN study_tag ON study.id = study_tag.study_id "
                + "JOIN tag ON tag.id = study_tag.tag_id "
                + "WHERE study.id IN (:ids)";

        return jdbcTemplate.queryForObject(sql, parameters, OWNER_WITH_TAG_ROW_MAPPER);
    }
}
