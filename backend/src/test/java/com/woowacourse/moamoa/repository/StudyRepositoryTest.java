package com.woowacourse.moamoa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.domain.Study;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@DataJdbcTest
@Sql("/init.sql")
public class StudyRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StudyRepository studyRepository;

    @BeforeEach
    void setUp() {
        studyRepository = new JdbcStudyRepository(jdbcTemplate);
    }

    @DisplayName("페이징 정보를 사용해 스터디 목록 조회")
    @Test
    public void findAll() {
        final List<Study> studies = studyRepository.findAll(2, 3);

        assertThat(studies)
                .hasSize(2)
                .filteredOn(study -> study.getId() != null)
                .extracting("title", "description", "thumbnail", "status")
                .containsExactlyInAnyOrder(
                        tuple("HTTP 스터디", "HTTP 설명", "http thumbnail", "CLOSE"),
                        tuple("알고리즘 스터디", "알고리즘 설명", "algorithm thumbnail", "CLOSE")
                );
    }
}
