package com.woowacourse.moamoa.tag.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.tag.domain.CategoryId;
import com.woowacourse.moamoa.tag.query.TagsSearcher;
import com.woowacourse.moamoa.tag.query.response.TagsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryTest
class SearchingTagControllerTest {

    @Autowired
    private TagsSearcher tagsSearcher;

    private SearchingTagController searchingTagController;

    @BeforeEach
    void setUp() {
        searchingTagController = new SearchingTagController(tagsSearcher);
    }

    @DisplayName("필터 목록 전체를 조회한다.")
    @Test
    void getFilters() {
        ResponseEntity<TagsResponse> response = searchingTagController.searchTags("", CategoryId.empty());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTags())
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject"),
                        tuple(2L, "4기", "우테코4기", 1L, "generation"),
                        tuple(3L, "BE", "백엔드", 2L, "area"),
                        tuple(4L, "FE", "프론트엔드", 2L, "area"),
                        tuple(5L, "React", "리액트", 3L, "subject")
                );
    }

    @DisplayName("필터 이름을 대소문자 구분없이 앞뒤 공백을 제거해 필터 목록을 조회한다.")
    @Test
    void getFiltersByName() {
        ResponseEntity<TagsResponse> response = searchingTagController.searchTags("   ja  \t ", CategoryId.empty());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTags())
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject")
                );
    }

    @Test
    void getFiltersByCategoryId() {
        ResponseEntity<TagsResponse> response = searchingTagController.searchTags("", new CategoryId(3L));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTags())
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject"),
                        tuple(5L, "React", "리액트", 3L, "subject")
                );
    }
}
