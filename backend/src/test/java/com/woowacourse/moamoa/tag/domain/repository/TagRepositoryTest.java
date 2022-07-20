package com.woowacourse.moamoa.tag.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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

    @DisplayName("tag id들로 각각 일치하는 Tag들을 찾은 후 반환한다.")
    @Test
    public void findAllById() {
        final List<Tag> tags = tagRepository.findAllById(List.of(1L, 2L, 3L));

        assertThat(tags)
                .hasSize(3)
                .filteredOn(tag -> tag.getId() != null)
                .extracting("name", "description")
                .contains(
                        tuple("Java", "자바"),
                        tuple("4기", "우테코4기"),
                        tuple("BE", "백엔드")
                );
    }
}
