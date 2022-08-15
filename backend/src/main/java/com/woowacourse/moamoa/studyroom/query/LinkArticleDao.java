package com.woowacourse.moamoa.studyroom.query;

import com.woowacourse.moamoa.member.query.data.MemberData;
import com.woowacourse.moamoa.studyroom.query.data.LinkArticleData;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LinkArticleDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Slice<LinkArticleData> findAllByStudyId(final Long studyId, final Pageable pageable) {
        final String sql = "SELECT link.id, link.link_url, link.description, link.created_date, link.last_modified_date, "
                + "member.github_id, member.username, member.image_url, member.profile_url "
                + "FROM link "
                + "JOIN member ON link.author_id = member.id "
                + "WHERE link.deleted = false "
                + "AND link.study_id = :studyId "
                + "ORDER BY link.created_date DESC, link.id DESC";
        final MapSqlParameterSource params = new MapSqlParameterSource("studyId", studyId);

        final List<LinkArticleData> linkData = namedParameterJdbcTemplate.query(sql, params, rowMapper());
        return new SliceImpl<>(getCurrentPageLinks(linkData, pageable), pageable, hasNext(linkData, pageable));
    }

    private List<LinkArticleData> getCurrentPageLinks(final List<LinkArticleData> linkData, final Pageable pageable) {
        if (hasNext(linkData, pageable)) {
            return linkData.subList(0, linkData.size() - 1);
        }
        return linkData;
    }

    private boolean hasNext(final List<LinkArticleData> linkData, final Pageable pageable) {
        return linkData.size() > pageable.getPageSize();
    }

    private RowMapper<LinkArticleData> rowMapper() {
        return (rs, rn) -> {
            final Long id = rs.getLong("id");
            final String linkUrl = rs.getString("link_url");
            final String description = rs.getString("description");
            final LocalDate createdDate = rs.getObject("created_date", LocalDate.class);
            final LocalDate lastModifiedDate = rs.getObject("last_modified_date", LocalDate.class);

            final Long githubId = rs.getLong("github_id");
            final String username = rs.getString("username");
            final String imageUrl = rs.getString("image_url");
            final String profileUrl = rs.getString("profile_url");
            final MemberData memberData = new MemberData(githubId, username, imageUrl, profileUrl);

            return new LinkArticleData(id, memberData, linkUrl, description, createdDate, lastModifiedDate);
        };
    }
}
