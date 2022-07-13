package com.woowacourse.moamoa.tag.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("태그 목록 전체를 조회한다.")
    @Test
    void findAll() {
        List<Tag> tags = tagRepository.findAll();

        assertThat(tags).hasSize(5)
                .filteredOn(tag -> tag.getId() != null)
                .extracting("name")
                .containsExactlyInAnyOrder("Java", "4기", "BE", "FE", "React");
    }

}