package com.woowacourse.moamoa.filter.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.woowacourse.moamoa.filter.domain.Filter;
import com.woowacourse.moamoa.filter.domain.repository.FilterRepository;
import com.woowacourse.moamoa.filter.service.FilterService;
import com.woowacourse.moamoa.filter.service.response.FilterResponse;
import com.woowacourse.moamoa.filter.service.response.FiltersResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class FilterControllerTest {

    private FilterRepository filterRepository;
    private FilterController filterController;

    @BeforeEach
    void setUp() {
        filterRepository = Mockito.mock(FilterRepository.class);
        when(filterRepository.findAllByNameContainingIgnoreCase(""))
                .thenReturn(List.of(
                        new Filter(1L, "Java"), new Filter(2L, "4기"), new Filter(3L, "BE")
                ));
        when(filterRepository.findAllByNameContainingIgnoreCase("ja"))
                .thenReturn(List.of(
                        new Filter(1L, "Java")
                ));
        filterController = new FilterController(new FilterService(filterRepository));
    }

    @DisplayName("태그 목록 전체를 조회한다.")
    @Test
    void getTags() {
        ResponseEntity<FiltersResponse> response = filterController.getTags("");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTags())
                .extracting(FilterResponse::getId, FilterResponse::getTagName)
                .containsExactly(
                        tuple(1L, "Java"),
                        tuple(2L, "4기"),
                        tuple(3L, "BE")
                );

        verify(filterRepository).findAllByNameContainingIgnoreCase("");
    }

    @DisplayName("태그 이름을 대소문자 구분없이 앞뒤 공백을 제거해 태그 목록을 조회한다.")
    @Test
    void getTagsByName() {
        ResponseEntity<FiltersResponse> response = filterController.getTags("   ja  \t ");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTags())
                .extracting(FilterResponse::getId, FilterResponse::getTagName)
                .containsExactly(
                        tuple(1L, "Java")
                );

        verify(filterRepository).findAllByNameContainingIgnoreCase("ja");
    }
}
