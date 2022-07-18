package com.woowacourse.moamoa.tag.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.tag.domain.CategoryId;
import com.woowacourse.moamoa.tag.query.response.TagsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class TagsSearcherTest {

    @Autowired
    private TagsSearcher tagsSearcher;

    @DisplayName("필터 없이 조회시 태그 목록 전체를 조회한다.")
    @Test
    void findAllByBlankTagName() {
        TagsResponse tagsResponse = tagsSearcher.searchBy("", CategoryId.empty());

        assertThat(tagsResponse.getTags())
                .hasSize(5)
                .extracting("id", "shortName", "fullName", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "SUBJECT"),
                        tuple(2L, "4기", "우테코4기", 1L, "GENERATION"),
                        tuple(3L, "BE", "백엔드", 2L, "AREA"),
                        tuple(4L, "FE", "프론트엔드", 2L, "AREA"),
                        tuple(5L, "React", "리액트", 3L, "SUBJECT")
                );
    }

    @DisplayName("대소문자 구분없이 필터 이름으로 조회한다.")
    @Test
    void findAllByNameContainingIgnoreCase() {
        TagsResponse tagsResponse = tagsSearcher.searchBy("ja", CategoryId.empty());

        assertThat(tagsResponse.getTags())
                .hasSize(1)
                .extracting("id", "shortName", "fullName", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "SUBJECT")
                );
    }

    @DisplayName("카테고리로 필터를 조회한다.")
    @Test
    void findAllByCategory() {
        TagsResponse tagsResponse = tagsSearcher.searchBy("", new CategoryId(3L));

        assertThat(tagsResponse.getTags())
                .hasSize(2)
                .extracting("id", "shortName", "fullName", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "SUBJECT"),
                        tuple(5L, "React", "리액트", 3L, "SUBJECT")
                );
    }

    @DisplayName("카테고리와 이름으로 필터를 조회한다.")
    @Test
    void findAllByCategoryAndName() {
        TagsResponse tagsResponse = tagsSearcher.searchBy("ja", new CategoryId(3L));

        assertThat(tagsResponse.getTags())
                .hasSize(1)
                .extracting("id", "shortName", "fullName", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "SUBJECT")
                );
    }
}
