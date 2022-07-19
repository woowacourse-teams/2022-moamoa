package com.woowacourse.moamoa.tag.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.moamoa.tag.domain.Tag;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @DisplayName("태그 없이 태그 조회시 태그 목록 전체를 조회한다.")
    @Test
    void findAllByBlankTagName() {
        List<Tag> tags = tagRepository.findAllByNameContainingIgnoreCase("");

        assertThat(tags).hasSize(5)
                .filteredOn(tag -> tag.getId() != null)
                .extracting("name")
                .containsExactlyInAnyOrder("Java", "4기", "BE", "FE", "React");
    }

    @DisplayName("대소문자 구분없이 태그 이름으로 조회한다.")
    @Test
    void findAllByNameContainingIgnoreCase() {
        List<Tag> tags = tagRepository.findAllByNameContainingIgnoreCase("ja");

        assertThat(tags).hasSize(1)
                .filteredOn(tag -> tag.getId() != null)
                .extracting("name")
                .containsExactlyInAnyOrder("Java");
    }
}
