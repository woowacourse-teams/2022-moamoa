package com.woowacourse.moamoa.study.query;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.study.domain.StudyStatus;
import com.woowacourse.moamoa.study.query.data.MyStudySummaryData;
import com.woowacourse.moamoa.tag.query.response.TagSummaryData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MyStudyDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public static final RowMapper<MyStudySummaryData> MY_STUDY_SUMMARY_ROW_MAPPER = (rs, rn) -> {
        final long id = rs.getLong("id");
        final String title = rs.getString("title");
        final String studyStatus = rs.getString("study_status");
        final int currentMemberCount = rs.getInt("current_member_count");
        final Integer maxMemberCount = rs.getObject("max_member_count", Integer.class);
        final String startDate = rs.getString("start_date");
        final String endDate = rs.getString("end_date");

        return new MyStudySummaryData(id, title, StudyStatus.valueOf(studyStatus),
                currentMemberCount, maxMemberCount, startDate, endDate);
    };

    private static final ResultSetExtractor<Map<Long, MemberData>> OWNER_ROW_MAPPER = rs -> {
        Map<Long, MemberData> result = new LinkedHashMap<>();

        Long studyId;
        while (rs.next()) {
            studyId = rs.getLong("study.id");

            if (!result.containsKey(studyId)) {
                Long id = rs.getLong("member_id");
                String username = rs.getString("username");
                String imageUrl = rs.getString("image_url");
                String profileUrl = rs.getString("profile_url");

                result.put(studyId, new MemberData(id, username, imageUrl, profileUrl));
            }
        }
        return result;
    };

    private static final ResultSetExtractor<Map<Long, List<TagSummaryData>>> TAG_ROW_MAPPER = rs -> {
        Map<Long, List<TagSummaryData>> result = new LinkedHashMap<>();

        while (rs.next()) {
            Long studyId = rs.getLong("study.id");

            if (!result.containsKey(studyId)) {
                result.put(studyId, new ArrayList<>());
            }

            final Long tagId = rs.getLong("tag.id");
            final String tagName = rs.getString("tag.name");

            result.get(studyId).add(new TagSummaryData(tagId, tagName));
        }
        return result;
    };

    public List<MyStudySummaryData> findMyStudyByMemberId(Long id) {
        String sql = "SELECT DISTINCT study.id, study.title, study.study_status, study.current_member_count, "
                + "study.max_member_count, study.start_date, study.end_date "
                + "FROM study LEFT JOIN study_member ON study_member.study_id = study.id "
                + "WHERE study_member.member_id = :id OR study.owner_id = :id ";

        return jdbcTemplate.query(sql, Map.of("id", id), MY_STUDY_SUMMARY_ROW_MAPPER);
    }

    public Map<Long, MemberData> findOwners(List<Long> studyIds) {
        if (studyIds.isEmpty()) {
            return new HashMap<>();
        }

        SqlParameterSource parameters = new MapSqlParameterSource("ids", studyIds);

        String sql = "SELECT study.id, member.id as member_id, member.username, member.image_url, member.profile_url "
                + "FROM study JOIN member ON member.id = study.owner_id "
                + "WHERE study.id IN (:ids)";

        return jdbcTemplate.query(sql, parameters, OWNER_ROW_MAPPER);
    }

    public Map<Long, List<TagSummaryData>> findTags(List<Long> studyIds) {
        if (studyIds.isEmpty()) {
            return new HashMap<>();
        }

        List<String> ids = studyIds.stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        SqlParameterSource parameters = new MapSqlParameterSource("ids", ids);

        String sql = "SELECT study.id, tag.id, tag.name "
                + "FROM study JOIN study_tag ON study.id = study_tag.study_id "
                + "JOIN tag ON tag.id = study_tag.tag_id "
                + "WHERE study.id IN (:ids)";

        return jdbcTemplate.query(sql, parameters, TAG_ROW_MAPPER);
    }
}
