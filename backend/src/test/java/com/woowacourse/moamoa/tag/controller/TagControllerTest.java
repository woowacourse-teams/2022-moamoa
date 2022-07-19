package com.woowacourse.moamoa.tag.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.woowacourse.moamoa.tag.domain.Tag;
import com.woowacourse.moamoa.tag.domain.repository.TagRepository;
import com.woowacourse.moamoa.tag.service.TagService;
import com.woowacourse.moamoa.tag.service.response.TagResponse;
import com.woowacourse.moamoa.tag.service.response.TagsResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class TagControllerTest {

    private TagRepository tagRepository;
    private TagController tagController;

    @BeforeEach
    void setUp() {
        tagRepository = Mockito.mock(TagRepository.class);
        when(tagRepository.findAllByNameContainingIgnoreCase(""))
                .thenReturn(List.of(
                        new Tag(1L, "Java"), new Tag(2L, "4기"), new Tag(3L, "BE")
                ));
        when(tagRepository.findAllByNameContainingIgnoreCase("ja"))
                .thenReturn(List.of(
                        new Tag(1L, "Java")
                ));
        tagController = new TagController(new TagService(tagRepository));
    }

    @DisplayName("태그 목록 전체를 조회한다.")
    @Test
    void getTags() {
        ResponseEntity<TagsResponse> response = tagController.getTags("");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTags())
                .extracting(TagResponse::getId, TagResponse::getTagName)
                .containsExactly(
                        tuple(1L, "Java"),
                        tuple(2L, "4기"),
                        tuple(3L, "BE")
                );

        verify(tagRepository).findAllByNameContainingIgnoreCase("");
    }

    @DisplayName("태그 이름을 대소문자 구분없이 앞뒤 공백을 제거해 태그 목록을 조회한다.")
    @Test
    void getTagsByName() {
        ResponseEntity<TagsResponse> response = tagController.getTags("   ja  \t ");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTags())
                .extracting(TagResponse::getId, TagResponse::getTagName)
                .containsExactly(
                        tuple(1L, "Java")
                );

        verify(tagRepository).findAllByNameContainingIgnoreCase("ja");
    }
}
