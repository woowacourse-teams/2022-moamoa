package com.woowacourse.moamoa.filter.infra;

import com.woowacourse.moamoa.filter.domain.CategoryId;
import com.woowacourse.moamoa.filter.infra.response.CategoryResponse;
import com.woowacourse.moamoa.filter.infra.response.FilterResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<FilterResponse> queryBy(final String name, final CategoryId categoryId) {
        return jdbcTemplate.query(sql(categoryId), param(name, categoryId), ROW_MAPPER);
    }

    private Map<String, Object> param(final String name, final CategoryId categoryId) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "%" + name + "%");
        param.put("categoryId", categoryId.getValue());
        return param;
    }

    private String sql(final CategoryId categoryId) {
        if (categoryId.isEmpty()) {
            return "SELECT f.id as filter_id, f.name as filter_name, "
                    + "c.id as category_id, c.name as category_name "
                    + "FROM filter as f JOIN category as c ON f.category_id = c.id "
                    + "WHERE UPPER(f.name) LIKE UPPER(:name)";
        }

        return "SELECT f.id as filter_id, f.name as filter_name, "
                + "c.id as category_id, c.name as category_name "
                + "FROM filter as f JOIN category as c ON f.category_id = c.id "
                + "WHERE UPPER(f.name) LIKE UPPER(:name) and c.id = :categoryId";
    }
}
