package com.woowacourse.moamoa.filter.infra;

import com.woowacourse.moamoa.filter.infra.response.CategoryResponse;
import com.woowacourse.moamoa.filter.infra.response.FilterResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FilterResponseDao {

    public static final RowMapper<FilterResponse> ROW_MAPPER = (rs, rn) -> {
        final long filterId = rs.getLong("filter_id");
        final String filterName = rs.getString("filter_name");
        final long categoryId = rs.getLong("category_id");
        final String categoryName = rs.getString("category_name");

        return new FilterResponse(filterId, filterName, new CategoryResponse(categoryId, categoryName));
    };

    private final JdbcTemplate jdbcTemplate;

    public List<FilterResponse> findAll(String name) {
        final String sql = "SELECT f.id as filter_id, f.name as filter_name, "
                + "c.id as category_id, c.name as category_name "
                + "FROM filter as f JOIN category as c ON f.category_id = c.id "
                + "WHERE UPPER(f.name) LIKE UPPER(?)";

        final String likeName = "%" + name + "%";
        return jdbcTemplate.query(sql, ROW_MAPPER, likeName);
    }
}
