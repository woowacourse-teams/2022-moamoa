package com.woowacourse.moamoa.repository;

import com.woowacourse.moamoa.domain.Study;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcStudyRepository implements StudyRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcStudyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Study> findAll(int page, int size) {
        final String sql = "SELECT id, title, description, thumbnail, status FROM study ORDER BY id LIMIT ? OFFSET ?";
        final int offset = (page - 1) * size;
        return jdbcTemplate.query(sql, (rs, rn) -> {
            Long id = rs.getLong("id");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String thumbnail = rs.getString("thumbnail");
            String status = rs.getString("status");
            return new Study(id, title, description, thumbnail, status);
        }, size, offset);
    }
}
