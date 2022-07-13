package com.woowacourse.moamoa.tag.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.woowacourse.moamoa.tag.domain.Tag;
import com.woowacourse.moamoa.tag.domain.repository.TagRepository;
import com.woowacourse.moamoa.tag.service.TagService;
import com.woowacourse.moamoa.tag.service.response.TagResponse;
import com.woowacourse.moamoa.tag.service.response.TagsResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class TagControllerTest {

    @DisplayName("태그 목록 전체를 조회한다.")
    @Test
    void getTags() {
        TagRepository tagRepository = Mockito.mock(TagRepository.class);

        when(tagRepository.findAll()).thenReturn(List.of(
           new Tag(1L, "Java"), new Tag(2L, "4기"), new Tag(3L, "BE")
        ));

        TagController tagController = new TagController(new TagService(tagRepository));

        ResponseEntity<TagsResponse> response = tagController.getTags();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTags())
                .extracting(TagResponse::getId, TagResponse::getTagName)
                .containsExactly(
                        tuple(1L, "Java"),
                        tuple(2L, "4기"),
                        tuple(3L, "BE")
                );

        verify(tagRepository).findAll();
    }

}