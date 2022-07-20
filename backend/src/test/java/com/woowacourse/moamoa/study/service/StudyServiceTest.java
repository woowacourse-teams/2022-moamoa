package com.woowacourse.moamoa.study.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.woowacourse.moamoa.member.domain.repository.MemberRepository;
import com.woowacourse.moamoa.member.service.response.MemberResponse;
import com.woowacourse.moamoa.study.domain.study.repository.StudyRepository;
import com.woowacourse.moamoa.study.service.response.StudyDetailResponse;
import com.woowacourse.moamoa.tag.domain.repository.TagRepository;
import com.woowacourse.moamoa.tag.query.response.TagResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@DataJpaTest
class StudyServiceTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TagRepository tagRepository;

    private StudyService studyService;

    @BeforeEach
    void setUp() {
        studyService = new StudyService(studyRepository, memberRepository, tagRepository);
    }

    @DisplayName("스터디 상세 정보를 조회할 수 있다.")
    @Test
    public void getStudyDetails() {
        final StudyDetailResponse studyDetails = studyService.getStudyDetails(1L);

        final MemberResponse owner = studyDetails.getOwner();
        final List<MemberResponse> members = studyDetails.getMembers();
        final List<TagResponse> tags = studyDetails.getTags();

        assertThat(studyDetails)
                .extracting("title", "excerpt", "thumbnail", "status", "description", "currentMemberCount",
                        "maxMemberCount", "createdAt", "enrollmentEndDate", "startDate", "endDate")
                .containsExactly("Java 스터디", "자바 설명", "java thumbnail", "OPEN", "그린론의 우당탕탕 자바 스터디입니다.", 1, 10,
                        "2021-11-08", "", "", "");

        assertThat(owner).extracting("githubId", "username", "imageUrl", "profileUrl")
                .containsExactly(2L, "greenlawn", "https://image", "github.com");

        assertThat(members).hasSize(0);
        assertThat(tags)
                .hasSize(3)
                .filteredOn(tag -> tag.getId() != null)
                .extracting("name", "description")
                .containsExactly(
                        tuple("Java", "자바"),
                        tuple("4기", "우테코4기"),
                        tuple("BE", "백엔드")
                );
    }
}
