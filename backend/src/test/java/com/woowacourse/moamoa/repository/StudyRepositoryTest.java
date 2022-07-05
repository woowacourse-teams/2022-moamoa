package com.woowacourse.moamoa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.domain.Study;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/init.sql")
public class StudyRepositoryTest {

    @Autowired
    private StudyRepository studyRepository;

    @DisplayName("페이징 정보를 사용해 스터디 목록 조회")
    @Test
    public void findAllByPageable() {
        final PageRequest pageRequest = PageRequest.of(1, 3);

        final Slice<Study> slice = studyRepository.findAllBy(pageRequest);

        assertThat(slice.hasNext()).isFalse();
        assertThat(slice.getContent())
                .hasSize(2)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "description", "thumbnail", "status")
                .containsExactlyInAnyOrder(
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE")
                );
    }
}
