package com.woowacourse.moamoa.filter.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.filter.infra.response.FilterResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

@DataJpaTest(includeFilters = @Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class FilterResponseDaoTest {

    @Autowired
    private FilterResponseDao filterResponseDao;

    @DisplayName("태그 없이 태그 조회시 태그 목록 전체를 조회한다.")
    @Test
    void findAllByBlankTagName() {
        List<FilterResponse> filterResponses = filterResponseDao.findAll("");

        assertThat(filterResponses)
                .hasSize(5)
                .filteredOn(filter -> filter.getId() != null)
                .extracting("name", "category.id", "category.name")
                .containsExactly(
                        tuple("Java", 3L, "TAG"),
                        tuple("4기", 1L, "GENERATION"),
                        tuple("BE", 2L, "AREA"),
                        tuple("FE", 2L, "AREA"),
                        tuple("React", 3L, "TAG")
                );
    }

    @DisplayName("대소문자 구분없이 태그 이름으로 조회한다.")
    @Test
    void findAllByNameContainingIgnoreCase() {
        List<FilterResponse> filterResponses = filterResponseDao.findAll("ja");

        assertThat(filterResponses)
                .hasSize(1)
                .filteredOn(filter -> filter.getId() != null)
                .extracting("name", "category.id", "category.name")
                .containsExactly(
                        tuple("Java", 3L, "TAG")
                );
    }
}
