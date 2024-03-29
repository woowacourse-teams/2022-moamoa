package com.woowacourse.moamoa.tag.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.alarm.SlackAlarmSender;
import com.woowacourse.moamoa.alarm.SlackUsersClient;
import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.tag.query.TagDao;
import com.woowacourse.moamoa.tag.query.request.CategoryIdRequest;
import com.woowacourse.moamoa.tag.service.SearchingTagService;
import com.woowacourse.moamoa.tag.service.response.TagsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RepositoryTest
@Import({RestTemplate.class, SlackAlarmSender.class, SlackUsersClient.class})
class SearchingTagControllerTest {

    @Autowired
    private TagDao tagDao;

    private SearchingTagController searchingTagController;

    @BeforeEach
    void setUp() {
        searchingTagController = new SearchingTagController(new SearchingTagService(tagDao));
    }

    @DisplayName("태그 목록 전체를 조회한다.")
    @Test
    void searchAllTags() {
        ResponseEntity<TagsResponse> response = searchingTagController.searchTags("", CategoryIdRequest.empty());

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

    @DisplayName("태그의 짧은 이름을 대소문자 구분없이 앞뒤 공백을 제거해 태그 목록을 조회한다.")
    @Test
    void searchTagsByShortName() {
        ResponseEntity<TagsResponse> response = searchingTagController.searchTags("   ja  \t ", CategoryIdRequest.empty());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTags())
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject")
                );
    }

    @DisplayName("카테고리 ID로 태그 목록을 조회한다.")
    @Test
    void searchTagsByCategoryId() {
        ResponseEntity<TagsResponse> response = searchingTagController.searchTags("", new CategoryIdRequest(3L));

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
