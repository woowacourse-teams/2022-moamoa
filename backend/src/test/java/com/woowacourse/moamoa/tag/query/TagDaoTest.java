package com.woowacourse.moamoa.tag.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.common.RepositoryTest;
import com.woowacourse.moamoa.tag.domain.CategoryId;
import com.woowacourse.moamoa.tag.query.response.TagData;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    @DisplayName("필터 없이 조회시 태그 목록 전체를 조회한다.")
    @Test
    void findAllByBlankTagName() {
        List<TagData> tagData = tagDao.getBy("", CategoryId.empty());

        assertThat(tagData)
                .hasSize(5)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject"),
                        tuple(2L, "4기", "우테코4기", 1L, "generation"),
                        tuple(3L, "BE", "백엔드", 2L, "area"),
                        tuple(4L, "FE", "프론트엔드", 2L, "area"),
                        tuple(5L, "React", "리액트", 3L, "subject")
                );
    }

    @DisplayName("대소문자 구분없이 태그 이름으로 조회한다.")
    @Test
    void findAllByNameContainingIgnoreCase() {
        List<TagData> tagData = tagDao.getBy("ja", CategoryId.empty());

        assertThat(tagData)
                .hasSize(1)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject")
                );
    }

    @DisplayName("카테고리로 태그를 조회한다.")
    @Test
    void findAllByCategory() {
        List<TagData> tagData = tagDao.getBy("", new CategoryId(3L));

        assertThat(tagData)
                .hasSize(2)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject"),
                        tuple(5L, "React", "리액트", 3L, "subject")
                );
    }

    @DisplayName("카테고리와 이름으로 태그를 조회한다.")
    @Test
    void findAllByCategoryAndName() {
        List<TagData> tagData = tagDao.getBy("ja", new CategoryId(3L));

        assertThat(tagData)
                .hasSize(1)
                .extracting("id", "name", "description", "category.id", "category.name")
                .containsExactly(
                        tuple(1L, "Java", "자바", 3L, "subject")
                );
    }
}
